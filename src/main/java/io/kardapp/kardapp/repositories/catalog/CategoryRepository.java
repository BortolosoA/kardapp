package io.kardapp.kardapp.repositories.catalog;

import io.kardapp.kardapp.domain.catalog.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Optional<Category>
    findByIdAndMenu_Restaurant_IdAndMenu_Restaurant_Tenant_Id(
            UUID categoryId, UUID restaurantId, UUID tenantId);
}
