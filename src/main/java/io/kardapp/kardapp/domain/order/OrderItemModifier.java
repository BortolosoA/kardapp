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
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "order_item_id", nullable = false)
    private OrderItem orderItem;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal priceDelta;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modifier_id", nullable = false)
    private Modifier modifier;
}
