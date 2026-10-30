package io.kardapp.kardapp.repositories.staff;

import io.kardapp.kardapp.domain.staff.StaffUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StaffUserRepository extends JpaRepository<StaffUser, UUID> {
}
