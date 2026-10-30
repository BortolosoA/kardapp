package io.kardapp.kardapp.repositories.table;

import io.kardapp.kardapp.domain.table.Tables;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TablesRepository extends JpaRepository<Tables, UUID> {
}
