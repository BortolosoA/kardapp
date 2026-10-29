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
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;

    @ManyToOne
    @JoinColumn(name = "allergen_id")
    private Allergen allergen;
}
