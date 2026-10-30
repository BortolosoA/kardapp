package io.kardapp.kardapp.domain.catalog;

import java.math.BigDecimal;
import java.util.UUID;

public record MenuItemDTO(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        boolean isAvailable,
        Category category

) {
}
