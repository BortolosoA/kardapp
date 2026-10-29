package io.kardapp.kardapp.domain.order;

import io.kardapp.kardapp.domain.customization.Modifier;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_item_modifiers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemModifier {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "order_item_id")
    private OrderItem orderItem;

    private BigDecimal priceDelta;

    @ManyToOne
    @JoinColumn(name = "modifier_id")
    private Modifier modifier;
}
