# Pendências e roteiro de implementação do Kardapp

Este documento descreve o que falta implementar e validar no estado atual do projeto. As tarefas abaixo são pendências, não funcionalidades já concluídas.

## Estado atual

- Aplicação Java 21 com Spring Boot, JPA, PostgreSQL e Flyway.
- Entidades e repositórios de organizações, restaurantes, catálogo, clientes, funcionários, mesas, pedidos e pagamentos.
- `MenuItemService` com listagem de produtos não arquivados e arquivamento limitado por tenant e restaurante.
- Arquivamento mantém o produto no banco e o marca como indisponível.
- Trigger que rejeita novos itens de pedido com produto inexistente, arquivado ou indisponível.
- Dois testes unitários de arquivamento e um teste de inicialização do contexto.
- Ainda não existem controllers, DTOs, autenticação nem serviço de criação de pedidos.

## Organização das migrations

A coluna `archived` e a constraint `menu_items_archived_unavailable` foram incorporadas ao `CREATE TABLE menu_items` da V3. A V6 ficou responsável apenas pela função e pela trigger de disponibilidade, pois depende de `order_items`, criada na V5.

Essa reorganização considera o ambiente de aprendizado em que o banco é reconstruído. Um banco vazio executará V1 até V6 em ordem. Apagar apenas os dados não reinicia o histórico do Flyway.

Quando houver um banco que precise ser preservado, mantenha migrations já aplicadas e faça alterações em novas versões. Não use reconstrução do banco como procedimento de atualização de ambientes com dados importantes.

## 1. Implementar o primeiro fluxo HTTP: produtos

### O que fazer

- [ ] Criar DTOs separados para cadastrar, editar e consultar produtos.
- [ ] Adicionar ao `MenuItemService` operações de cadastro e edição.
- [ ] Criar `MenuItemController` para receber as requisições HTTP.
- [ ] Fazer a operação de remoção chamar `archive(...)`, sem executar exclusão física.
- [ ] Validar se a categoria pertence ao restaurante e ao tenant autorizados.
- [ ] Retornar DTOs em vez de entidades JPA diretamente.

### Como pensar nas responsabilidades

```text
Controller: recebe e responde HTTP
    -> Service: aplica regras e controla a transação
        -> Repository: consulta e persiste
            -> PostgreSQL: armazena e garante constraints
```

Um DTO é uma classe ou `record` com os campos que a API aceita ou devolve. O cliente pode enviar nome, preço e ID da categoria, mas não deve controlar diretamente o ID gerado, o estado de arquivamento ou vínculos com outra empresa.

### Critérios de conclusão

- Cadastrar um produto válido retorna seu ID.
- Consultar o cardápio retorna os campos públicos necessários.
- Arquivar o produto o remove da listagem sem apagar seu registro.
- Tentar alterar um produto fora do escopo autorizado não o modifica.

## 2. Validar entradas e padronizar erros

### O que fazer

- [ ] Adicionar suporte a Bean Validation, por exemplo com `spring-boot-starter-validation`.
- [ ] Usar `@Valid` nos DTOs recebidos pelos controllers.
- [ ] Validar nomes obrigatórios e tamanhos máximos com `@NotBlank` e `@Size`.
- [ ] Validar valores monetários e quantidades conforme as regras do negócio.
- [ ] Usar `@Email` quando houver e-mail informado.
- [ ] Criar exceções de negócio específicas em vez de depender de `IllegalArgumentException` para todos os casos.
- [ ] Criar um `@RestControllerAdvice` para transformar exceções em respostas consistentes.

`@Column(nullable = false)` impede `NULL` no banco, mas não impede um nome vazio. A validação do DTO devolve um erro compreensível antes da tentativa de persistência.

### Respostas esperadas

| Caso | HTTP sugerido |
| --- | --- |
| Dados inválidos | 400 |
| Usuário sem autenticação em rota protegida | 401 |
| Usuário sem permissão | 403 |
| Recurso não encontrado no escopo autorizado | 404 |
| Conflito com o estado atual | 409 |

Para não revelar a existência de recursos de outras empresas, uma busca limitada ao tenant pode retornar 404. Não retorne stack traces nem detalhes internos do banco ao cliente.

## 3. Implementar autenticação e isolamento entre tenants

### O que fazer

- [ ] Definir como os funcionários se autenticam: credenciais locais ou provedor externo.
- [ ] Adicionar e configurar Spring Security para as rotas administrativas.
- [ ] Se usar senhas locais, armazenar somente hashes apropriados, nunca texto puro.
- [ ] Obter o tenant a partir da identidade autenticada e dos vínculos autorizados.
- [ ] Definir permissões de `OWNER`, `MANAGER` e `STAFF`.
- [ ] Separar consulta pública do cardápio das operações administrativas.
- [ ] Limitar consultas e alterações ao tenant e restaurante corretos.
- [ ] Definir a autorização de pedidos públicos por QR code; eles não devem depender de permissões administrativas.

Receber `tenantId` em um método não comprova autorização. O controller não deve confiar em um tenant arbitrário enviado pelo cliente. O filtro atual do serviço de produtos precisa ser conectado a um contexto autenticado confiável.

### Integridade dos vínculos

Ao criar ou alterar dados, validar também:

- Mesa e pedido pertencem ao mesmo restaurante.
- Cliente pertence ao tenant do restaurante.
- Produto pertence ao restaurante do pedido.
- Adicional pertence ao produto escolhido.

As FKs atuais garantem a existência dos registros, mas não essas combinações. Para reforçar o banco, avaliar constraints compostas e as colunas necessárias, sem duplicar IDs de escopo sem uma regra que mantenha sua consistência.

## 4. Completar menus, categorias e adicionais

- [ ] Criar serviços, DTOs e controllers para menus, categorias e adicionais.
- [ ] Aplicar `isActive` e horários de disponibilidade do menu na consulta pública e na compra.
- [ ] Definir como tratar menus cujo horário atravessa meia-noite e qual fuso usar por restaurante.
- [ ] Decidir se produtos temporariamente indisponíveis ficam visíveis com aviso ou são ocultados.
- [ ] Manter produtos arquivados fora do cardápio público.
- [ ] Definir como retirar adicionais do catálogo preservando seus usos em pedidos anteriores.
- [ ] Validar mínimo e máximo de escolhas de cada grupo de adicionais.

A consulta atual de produtos filtra `archived`, mas não verifica menu ativo nem seu horário. Não trate essa listagem isolada como um cardápio público completo.

## 5. Implementar criação e alteração de pedidos

### Fluxo de criação

- [ ] Criar um DTO com IDs dos produtos, quantidades, adicionais e observações.
- [ ] Criar um `OrderService` com uma operação `@Transactional`.
- [ ] Validar restaurante, mesa, cliente e todos os vínculos de tenant.
- [ ] Validar produto, menu e adicionais disponíveis.
- [ ] Rejeitar quantidade zero ou negativa.
- [ ] Buscar preços no banco e calcular o total no servidor.
- [ ] Gravar os preços cobrados em `OrderItem` e `OrderItemModifier`.
- [ ] Salvar pedido, itens e adicionais na mesma transação.
- [ ] Se qualquer validação ou gravação falhar, reverter a operação inteira.

Não aceite o total calculado pelo cliente como valor oficial. Defina como adicionais entram no cálculo; por exemplo, adicionais por unidade podem compor `(preço unitário + adicionais) × quantidade`. Estabeleça arredondamento e precisão monetária.

### Alterações após a criação

- [ ] Definir quais estados permitem editar quantidades, trocar produtos e adicionar itens.
- [ ] Proibir transferência arbitrária de itens entre pedidos.
- [ ] Recalcular o total quando uma alteração permitida afetar o valor.
- [ ] Definir como lidar com aumento de quantidade de produto posteriormente arquivado ou indisponível.

### Limites atuais da trigger

A V6 verifica inserções e mudanças de `menu_item_id`. Ela não verifica alteração apenas de quantidade ou de `order_id`. Também não valida menu ativo, horários ou escopo de restaurante. Completar as regras no serviço e avaliar quais precisam de reforço no banco.

- [ ] Tornar a mensagem da trigger clara para produtos inexistentes, arquivados ou indisponíveis; atualmente diz apenas `Produto inexistente`.
- [ ] Preservar a proteção contra concorrência ao modificar a trigger: uma compra e um arquivamento simultâneos precisam de um resultado consistente.

## 6. Controlar status, pagamentos e concorrência

- [ ] Definir as transições permitidas de `OrderStatus` para cada tipo de pedido.
- [ ] Substituir setters irrestritos dos campos críticos por operações como iniciar preparo e cancelar.
- [ ] Definir se pagamento e preparo são estados independentes; o enum atual mistura `PAID` com estados de atendimento.
- [ ] Criar serviço de registro e confirmação de pagamentos.
- [ ] Definir se haverá pagamento parcial, estorno e cancelamento após pagamento.
- [ ] Atualizar `paidAt` ao confirmar o pagamento, conforme a regra definida.
- [ ] Evitar processar duas vezes a mesma confirmação de pagamento.
- [ ] Avaliar `@Version` em entidades alteradas simultaneamente e adicionar suas colunas às migrations.
- [ ] Tratar conflitos de atualização com resposta compreensível.

`@Version` permite detectar que outro processo já alterou um registro. Não substitui validações de negócio, transações ou uma estratégia para confirmações duplicadas.

## 7. Reforçar constraints e índices

- [ ] `order_items.quantity > 0`.
- [ ] Preços e valores não negativos onde essa for a regra definida.
- [ ] `modifier_groups.min_select >= 0`.
- [ ] `modifier_groups.max_select >= min_select`.
- [ ] Unicidade de `(menu_item_id, allergen_id)` em `menu_item_allergens`.
- [ ] Definir limites válidos para lugares de uma mesa.
- [ ] Alinhar comprimentos de colunas entre enums, entidades e SQL; por exemplo, `staff_users.role` tem tamanho 7 no SQL, mas a entidade não informa esse limite.
- [ ] Adicionar índices conforme os filtros por tenant, restaurante, categoria e pedidos.

Não proíba automaticamente `priceDelta` negativo: ele pode representar desconto, se essa funcionalidade existir. Escolha os índices considerando as consultas; FKs não criam automaticamente índices nas colunas que referenciam outras tabelas no PostgreSQL.

## 8. Preservar o histórico completo

- [ ] Manter produtos arquivados acessíveis aos pedidos antigos.
- [ ] Registrar o nome do produto no item do pedido no momento da compra.
- [ ] Registrar o nome do adicional no adicional do item do pedido.
- [ ] Consultar os dados históricos salvos, sem recalcular usando o preço atual do catálogo.
- [ ] Definir se mesa e cliente precisam de informações históricas além de suas referências atuais.

Os preços já possuem campos próprios no pedido. Os nomes ainda dependem do cadastro atual: renomear um produto pode mudar a apresentação de pedidos antigos. O arquivamento preserva a referência, mas não cria uma cópia histórica de todos os seus atributos.

## 9. Ajustar carregamento e respostas da API

- [ ] Avaliar `FetchType.LAZY` nos `@ManyToOne`, que hoje usam o padrão `EAGER`.
- [ ] Buscar explicitamente os relacionamentos necessários com projeções, `join fetch` ou `@EntityGraph`.
- [ ] Montar DTOs dentro de um fluxo que tenha os dados necessários carregados.
- [ ] Evitar consultas extras por registro ao listar pedidos e itens.
- [ ] Paginar listagens que podem crescer, como histórico de pedidos.

Primeiro implemente e observe as consultas reais. Trocar tudo para `LAZY` sem controlar as buscas pode apenas trocar consultas excessivas por erros de acesso fora da sessão.

## 10. Testar os fluxos com PostgreSQL

Os testes unitários atuais usam Mockito. Eles verificam objetos Java e chamadas ao repositório, mas não executam as queries derivadas, migrations ou trigger.

- [ ] Configurar um PostgreSQL exclusivo de testes, por exemplo com Testcontainers, se Docker estiver disponível.
- [ ] Executar V1 até V6 em um banco vazio e iniciar o contexto Spring.
- [ ] Validar os métodos derivados de `MenuItemRepository` com dados reais.
- [ ] Persistir um pedido, arquivar seu produto e consultar o pedido novamente.
- [ ] Confirmar que produto arquivado ou indisponível não entra em novo item de pedido.
- [ ] Testar alteração de quantidade e tentativa de transferência de item, conforme as regras escolhidas.
- [ ] Testar vínculos e acessos entre empresas diferentes.
- [ ] Confirmar rollback integral de uma criação de pedido com falha.
- [ ] Testar transições de status e confirmações duplicadas de pagamento.
- [ ] Testar compra e arquivamento concorrentes.

O teste atual `contextLoads` depende da configuração de banco da aplicação. Separe essa configuração para tornar os testes reproduzíveis sem depender do banco usado durante o desenvolvimento.

## 11. Implementar encerramento da conta e exportação

Deixar para depois do fluxo principal de catálogo e pedidos.

- [ ] Marcar a conta como em encerramento e bloquear novas operações.
- [ ] Gerar PDF para consulta e JSON ou CSV para recuperação estruturada.
- [ ] Enviar ao S3 e confirmar integridade e conclusão do envio.
- [ ] Registrar o andamento fora dos dados que serão excluídos, para permitir retomada em caso de falha.
- [ ] Excluir somente os dados do tenant, em ordem compatível com as FKs e dentro de uma transação quando viável.
- [ ] Preservar dados globais compartilhados, como o catálogo de alérgenos.
- [ ] Definir acesso e prazo de exclusão dos arquivos exportados.
- [ ] Gerar novo UUID para uma nova conta; tratar eventual reutilização de slug separadamente.

Com as FKs atuais, excluir apenas o tenant não remove automaticamente todos os dados: pedidos e outras dependências podem bloquear a operação. O encerramento precisa de um fluxo explícito.

## 12. Documentar execução e preparar entrega

- [ ] Criar um README com Java necessário, banco, variáveis de ambiente e comandos de execução.
- [ ] Documentar como executar testes e reconstruir o banco de desenvolvimento.
- [ ] Manter credenciais fora do Git e disponibilizar somente exemplos sem valores reais.
- [ ] Documentar os endpoints à medida que forem criados.
- [ ] Automatizar compilação e testes em integração contínua quando o primeiro fluxo estiver funcionando.

## Ordem sugerida de estudo

1. DTOs, validação e cadastro/consulta de produtos.
2. Controllers e tratamento de erros.
3. Autenticação, autorização e tenant confiável.
4. Menus, categorias e adicionais.
5. Criação de pedidos com transações e cálculo de valores.
6. Histórico, estados e pagamentos.
7. Concorrência e ajustes nas consultas conforme os fluxos reais.
8. Exportação e encerramento de conta.

Adicione testes e constraints junto de cada funcionalidade, sem esperar terminar toda a aplicação. A arquitetura em camadas atual é suficiente para esse roteiro; não é necessário migrar para microserviços ou reorganizar tudo antes de continuar.
