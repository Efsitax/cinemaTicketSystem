package com.Softito.cinemaTicketSystem.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Sessions")
public class Session {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="session_id")
    private Long sessionsId;


    @ManyToOne
    @JoinColumn(name = "film_id",referencedColumnName = "film_id")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "saloon_id",referencedColumnName = "saloon_id")
    private Saloon saloon;

}
