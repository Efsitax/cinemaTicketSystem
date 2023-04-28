package com.Softito.cinemaTicketSystem.Services;

import com.Softito.cinemaTicketSystem.Model.Film;
import com.Softito.cinemaTicketSystem.Model.Role;
import com.Softito.cinemaTicketSystem.Repository.FilmRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class FilmService implements IBaseService<Film>{
    @Autowired
    private FilmRepository filmRepository;
    @Override
    public List<Film> getAll() {
        return filmRepository.findAll();
    }

    @Override
    public Film getById(Long id) {
        return filmRepository.findById(id).orElse(null);
    }

    @Override
    public Film create(Film entity) {
        return filmRepository.save(entity);
    }

    @Override
    public Film update(Long id, Film entity) {
        Film existingFilm = getById(id);
        if (existingFilm != null) {
            existingFilm.setName(entity.getName());
            return filmRepository.save(existingFilm);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if(getById(id)!=null){
            filmRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
