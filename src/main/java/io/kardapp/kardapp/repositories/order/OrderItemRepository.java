package io.kardapp.kardapp.repositories.order;

import io.kardapp.kardapp.domain.order.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
