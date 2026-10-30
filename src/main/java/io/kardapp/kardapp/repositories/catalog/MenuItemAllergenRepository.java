package io.kardapp.kardapp.repositories.catalog;

import io.kardapp.kardapp.domain.catalog.MenuItemAllergen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MenuItemAllergenRepository extends JpaRepository<MenuItemAllergen, UUID> {
}
