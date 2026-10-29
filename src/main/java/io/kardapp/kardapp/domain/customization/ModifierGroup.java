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
    @Column(nullable = false)
    private UUID id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private int minSelect;
    @Column(nullable = false)
    private int maxSelect;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_item_id", nullable = false)
    private MenuItem menuItem;
}
