package com.Softito.cinemaTicketSystem.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Saloons")
public class Saloon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="saloon_id")
    private Long saloonId;

    @Column(name = "available")
    private Boolean available;

    @Column(name = "capacity")
    private Long capacity;
    @JsonCreator
    public Saloon(@JsonProperty("saloon_id") Long id) {
        this.saloonId = id;
    }
    public Saloon(){

    }

}
