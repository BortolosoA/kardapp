package io.kardapp.kardapp.domain.catalog;

import io.kardapp.kardapp.domain.tenant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;
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
    @Column(nullable = false)
    private UUID id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private boolean isActive;

    private LocalTime availableFrom;
    private LocalTime availableTo;

    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id", nullable = false )
    private Restaurant restaurant;
}
