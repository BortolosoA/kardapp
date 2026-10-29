package io.kardapp.kardapp.domain.staff;

import io.kardapp.kardapp.domain.tenant.Restaurant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "staff_users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StaffUser {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String email;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private Restaurant restaurant;

    @Enumerated(EnumType.STRING)
    private StaffUserRole role;
}
