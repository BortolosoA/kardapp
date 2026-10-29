package io.kardapp.kardapp.domain.customization;

import io.kardapp.kardapp.domain.catalog.MenuItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "modifier_groups")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModifierGroup {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private int minSelect;
    private int maxSelect;

    @ManyToOne
    @JoinColumn(name = "menu_item_id")
    private MenuItem menuItem;
}
