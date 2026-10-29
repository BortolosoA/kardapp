package io.kardapp.kardapp.domain.catalog;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "allergens")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Allergen {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
}
