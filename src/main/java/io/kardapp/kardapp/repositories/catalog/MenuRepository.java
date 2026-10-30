package io.kardapp.kardapp.repositories.catalog;

import io.kardapp.kardapp.domain.catalog.Menu;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.UUID;

public interface MenuRepository extends JpaRepository<Menu, UUID> {
}
