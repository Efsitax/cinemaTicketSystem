package com.Softito.cinemaTicketSystem.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

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
    @Column(name = "language")
    private String language;
    @Column(name = "duration")
    private Long duration;
    @Column(name = "price")
    private Long price;
    @Column(name = "is_active")
    private Boolean isActive;
    @Column(name = "description", columnDefinition = "text")
    private String description;
    @Lob
    @Column(name = "photo", length = Integer.MAX_VALUE, nullable = true)
    private byte[] image;
    @JsonCreator
    public Film(@JsonProperty("film_id") Long id) {
        this.filmId = id;
    }
    public Film(){

    }


}
