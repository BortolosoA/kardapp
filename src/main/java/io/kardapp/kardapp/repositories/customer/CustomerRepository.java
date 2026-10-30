package io.kardapp.kardapp.repositories.customer;

import io.kardapp.kardapp.domain.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
