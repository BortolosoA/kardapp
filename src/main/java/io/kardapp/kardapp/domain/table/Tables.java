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

    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String label;

    @Column(nullable = false, unique = true, length = 200)
    private String qrCode;
    @Column(nullable = false)
    private int seats;
    @Column(nullable = false)
    private boolean inUse;


    @ManyToOne(optional = false)
    @JoinColumn(name = "restaurant_id",nullable = false)
    private Restaurant restaurant;
}
