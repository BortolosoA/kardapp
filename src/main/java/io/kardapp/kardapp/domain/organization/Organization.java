package io.kardapp.kardapp.domain.organization;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "organization")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Organization {

    @Id
    @GeneratedValue
    private UUID id;
    private String cnpjName;
    private String fantasyName;
    private String cnpj;
    private Long ie;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
