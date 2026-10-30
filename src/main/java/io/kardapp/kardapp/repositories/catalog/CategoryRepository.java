package io.kardapp.kardapp.repositories.catalog;

import io.kardapp.kardapp.domain.catalog.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
}
