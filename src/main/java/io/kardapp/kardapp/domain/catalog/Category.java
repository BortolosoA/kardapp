package io.kardapp.kardapp.domain.catalog;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private int sortOrder;

    @ManyToOne
    @JoinColumn(name = "menu_id")
    private Menu menu;

}
