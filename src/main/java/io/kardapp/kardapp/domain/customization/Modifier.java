package io.kardapp.kardapp.domain.customization;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "modifiers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Modifier {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private BigDecimal priceDelta;

    @ManyToOne
    @JoinColumn(name = "modifier_group_id")
    private ModifierGroup modifierGroup;
}
