package com.Softito.cinemaTicketSystem.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Films")
public class Film {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="film_id")
    private Long filmId;
    @Column(name = "name")
    private String name;
    @Column(name = "duration")
    private String Duration;
    @Column(name = "price")
    private String Price;
    @Column(name = "is_active")
    private Boolean isActive;



}
