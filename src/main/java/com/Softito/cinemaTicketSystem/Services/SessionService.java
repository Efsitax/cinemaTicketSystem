package com.Softito.cinemaTicketSystem.Services;

import com.Softito.cinemaTicketSystem.Model.Session;
import com.Softito.cinemaTicketSystem.Repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SessionService implements IBaseService<Session>{
    @Autowired
    private SessionRepository repository;

    @Override
    public List<Session> getAll() {
        return repository.findAll();
    }

    @Override
    public Session getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Session create(Session entity) {
        return repository.save(entity);
    }

    @Override
    public Session update(Long id, Session entity) {
        Session existingSession = getById(id);
        if (existingSession != null) {
            existingSession.setFilm(entity.getFilm());
            existingSession.setSaloon(entity.getSaloon());
            return repository.save(existingSession);
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
