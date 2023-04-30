package com.Softito.cinemaTicketSystem.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
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
    private Long sessionId;
    @ManyToOne
    @JoinColumn(name = "film_id",referencedColumnName = "film_id")
    private Film film;
    @ManyToOne
    @JoinColumn(name = "saloon_id",referencedColumnName = "saloon_id")
    private Saloon saloon;

    @JsonCreator
    public Session(@JsonProperty("session_id") Long id) {
        this.sessionId = id;
    }
    public Session(){

    }
}
