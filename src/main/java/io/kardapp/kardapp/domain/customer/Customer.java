package io.kardapp.kardapp.domain.customer;

import io.kardapp.kardapp.domain.tenant.Tenant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "costumers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Customer {

    @Id
    @GeneratedValue
    private UUID id;
    private String name;
    private String phone;
    private String email;

    @ManyToOne
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;
}
