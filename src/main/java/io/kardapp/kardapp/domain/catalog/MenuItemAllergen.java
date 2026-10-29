package io.kardapp.kardapp.domain.catalog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "menu_item_allergens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemAllergen {

    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "allergen_id", nullable = false)
    private Allergen allergen;
}
