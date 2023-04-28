package com.Softito.cinemaTicketSystem.Controller;


import com.Softito.cinemaTicketSystem.Model.Film;
import com.Softito.cinemaTicketSystem.Services.FilmService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/films")
public class FilmController {
    @Autowired
    private FilmService service ;

    @GetMapping("")
    public List<Film> getAllFilms() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public Film createFilm(@RequestBody Film entity) {
        return service.create(entity);
    }
    @DeleteMapping("/delete/{id}")
    public Boolean deleteFilm(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/update/{id}")
    public Film updateFilm(@PathVariable Long id, @RequestBody Film entity) {
        return service.update(id, entity);
    }
}