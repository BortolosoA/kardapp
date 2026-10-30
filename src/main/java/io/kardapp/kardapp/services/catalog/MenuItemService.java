package io.kardapp.kardapp.services.catalog;

import io.kardapp.kardapp.domain.catalog.MenuItem;
import io.kardapp.kardapp.repositories.catalog.MenuItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class MenuItemService {

    private final MenuItemRepository repository;

    public MenuItemService(MenuItemRepository repository) {
        this.repository = repository;
    }

    // tenantId must come from the authenticated user's authorized context.
    @Transactional
    public void archive(UUID tenantId, UUID restaurantId, UUID itemId) {
        MenuItem item = repository
                .findByIdAndCategory_Menu_Restaurant_IdAndCategory_Menu_Restaurant_Tenant_Id(
                        itemId, restaurantId, tenantId)
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

        item.archive();
    }

    @Transactional(readOnly = true)
    public List<MenuItem> listMenuItems(UUID tenantId, UUID restaurantId, UUID categoryId) {
        return repository
                .findByCategory_IdAndCategory_Menu_Restaurant_IdAndCategory_Menu_Restaurant_Tenant_IdAndArchivedFalseOrderBySortOrderAscIdAsc(
                        categoryId, restaurantId, tenantId);
    }
}
