package io.kardapp.kardapp.repositories.order;

import io.kardapp.kardapp.domain.order.OrderItemModifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemModifierRepository extends JpaRepository<OrderItemModifier, UUID> {
}
