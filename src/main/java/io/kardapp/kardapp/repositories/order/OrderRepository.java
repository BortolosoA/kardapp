package io.kardapp.kardapp.repositories.order;

import io.kardapp.kardapp.domain.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
}
