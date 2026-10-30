package io.kardapp.kardapp.repositories.tenant;

import io.kardapp.kardapp.domain.tenant.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RestaurantRepository extends JpaRepository<Restaurant, UUID> {
}
