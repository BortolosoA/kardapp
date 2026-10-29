package io.kardapp.kardapp.domain.catalog;

import io.kardapp.kardapp.domain.tenant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "menus")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private boolean isActive;

    private LocalDateTime availableFrom;
    private LocalDateTime availableTo;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
