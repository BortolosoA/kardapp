package io.kardapp.kardapp.services.catalog;

import io.kardapp.kardapp.domain.catalog.MenuItem;
import io.kardapp.kardapp.domain.order.OrderItem;
import io.kardapp.kardapp.repositories.catalog.MenuItemRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuItemServiceTests {

    @Test
    void archivalPreservesHistoricalOrderReferenceAndChargedPrice() {
        MenuItem product = new MenuItem();
        UUID productId = UUID.randomUUID();
        product.setId(productId);
        product.setName("Lasanha");
        product.setAvailable(true);

        OrderItem historicalItem = new OrderItem();
        historicalItem.setMenuItem(product);
        historicalItem.setQuantity(2);
        historicalItem.setUnitPrice(new BigDecimal("35.00"));

        MenuItemRepository repository = mock(MenuItemRepository.class);
        UUID tenantId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        when(repository.findByIdAndCategory_Menu_Restaurant_IdAndCategory_Menu_Restaurant_Tenant_Id(
                productId, restaurantId, tenantId)).thenReturn(Optional.of(product));

        MenuItemService service = new MenuItemService(repository);
        service.archive(tenantId, restaurantId, productId);
        service.archive(tenantId, restaurantId, productId);

        assertTrue(product.isArchived());
        assertFalse(product.isAvailable());
        assertSame(product, historicalItem.getMenuItem());
        assertEquals(productId, historicalItem.getMenuItem().getId());
        assertEquals("Lasanha", historicalItem.getMenuItem().getName());
        assertEquals(new BigDecimal("35.00"), historicalItem.getUnitPrice());
        assertEquals(2, historicalItem.getQuantity());
    }

    @Test
    void archivalRejectsProductOutsideRequestedTenantAndRestaurant() {
        MenuItemRepository repository = mock(MenuItemRepository.class);
        UUID itemId = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();
        UUID tenantId = UUID.randomUUID();
        when(repository.findByIdAndCategory_Menu_Restaurant_IdAndCategory_Menu_Restaurant_Tenant_Id(
                itemId, restaurantId, tenantId)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> new MenuItemService(repository).archive(tenantId, restaurantId, itemId));

        verify(repository).findByIdAndCategory_Menu_Restaurant_IdAndCategory_Menu_Restaurant_Tenant_Id(
                itemId, restaurantId, tenantId);
        verifyNoMoreInteractions(repository);
    }
}
