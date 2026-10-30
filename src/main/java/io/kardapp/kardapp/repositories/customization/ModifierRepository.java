package io.kardapp.kardapp.repositories.customization;

import io.kardapp.kardapp.domain.customization.Modifier;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModifierRepository extends JpaRepository<Modifier, UUID> {
}
