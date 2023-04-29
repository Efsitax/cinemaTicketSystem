package com.Softito.cinemaTicketSystem.Model;

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
    private String isactive;

    @Column(name = "capacity")
    private Long capacity;



}
