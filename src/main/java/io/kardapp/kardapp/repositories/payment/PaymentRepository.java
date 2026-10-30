package io.kardapp.kardapp.repositories.payment;

import io.kardapp.kardapp.domain.payment.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
}
