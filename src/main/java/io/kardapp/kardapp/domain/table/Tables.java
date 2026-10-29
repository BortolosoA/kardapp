package io.kardapp.kardapp.domain.table;

import io.kardapp.kardapp.domain.tenant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "tables")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Tables {

    @Id
    @GeneratedValue
    private UUID id;
    private String label;
    private String qrCode;
    private int seats;
    private boolean inUse;


    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;
}
