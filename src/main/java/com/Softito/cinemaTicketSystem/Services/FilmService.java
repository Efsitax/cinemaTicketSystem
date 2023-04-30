package com.Softito.cinemaTicketSystem.Services;

import com.Softito.cinemaTicketSystem.Model.Film;
import com.Softito.cinemaTicketSystem.Repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FilmService implements IBaseService<Film>{
    @Autowired
    private FilmRepository repository;
    @Override
    public List<Film> getAll() {
        return repository.findAll();
    }

    @Override
    public Film getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Film create(Film entity) {
        return repository.save(entity);
    }

    @Override
    public Film update(Long id, Film entity) {
        Film existingFilm = getById(id);
        if (existingFilm != null) {
            existingFilm.setName(entity.getName());
            existingFilm.setDuration(entity.getDuration());
            existingFilm.setPrice(entity.getPrice());
            existingFilm.setIsActive(entity.getIsActive());
            return repository.save(existingFilm);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(getById(id)!=null){
            repository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
