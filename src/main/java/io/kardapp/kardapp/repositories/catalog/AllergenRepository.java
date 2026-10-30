package io.kardapp.kardapp.repositories.catalog;

import io.kardapp.kardapp.domain.catalog.Allergen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AllergenRepository extends JpaRepository<Allergen, UUID> {
}
