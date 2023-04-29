package com.Softito.cinemaTicketSystem.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="role_id")
    private Long role_id;
    @Column(name = "name")
    private String name;
    @JsonCreator
    public Role(@JsonProperty("role_id") Long id) {
        this.role_id = id;
    }

}
