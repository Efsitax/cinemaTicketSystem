package com.Softito.cinemaTicketSystem.Repository;

import com.Softito.cinemaTicketSystem.Model.Film;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long> {

}
