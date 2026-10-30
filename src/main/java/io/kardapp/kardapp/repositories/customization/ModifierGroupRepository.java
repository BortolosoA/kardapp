package io.kardapp.kardapp.repositories.customization;

import io.kardapp.kardapp.domain.customization.ModifierGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ModifierGroupRepository extends JpaRepository<ModifierGroup, UUID> {
}
