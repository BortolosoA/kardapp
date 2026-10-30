package io.kardapp.kardapp.repositories.catalog;


import io.kardapp.kardapp.domain.catalog.MenuItem;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MenuItemRepository extends Repository<MenuItem, UUID> {
    MenuItem save(MenuItem item);

    Optional<MenuItem>
    findByIdAndCategory_Menu_Restaurant_IdAndCategory_Menu_Restaurant_Tenant_Id(
            UUID itemId,
            UUID restaurantId,
            UUID tenantId
            );

    List<MenuItem>
    findByArchivedFalseAndCategory_Menu_Restaurant_IdAndCategory_Menu_Tenant_Id(
            UUID restaurantId,
            UUID tenantId
    );
}
