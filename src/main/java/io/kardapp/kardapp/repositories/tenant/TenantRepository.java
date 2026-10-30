package io.kardapp.kardapp.repositories.tenant;

import io.kardapp.kardapp.domain.tenant.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {
}
