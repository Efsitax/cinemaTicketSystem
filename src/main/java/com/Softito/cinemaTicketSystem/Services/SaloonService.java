package com.Softito.cinemaTicketSystem.Services;

import com.Softito.cinemaTicketSystem.Model.Saloon;
import com.Softito.cinemaTicketSystem.Repository.SaloonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SaloonService implements IBaseService<Saloon>{
    @Autowired
    private SaloonRepository repository;
    @Override
    public List<Saloon> getAll() {
        return repository.findAll();
    }

    @Override
    public Saloon getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Saloon create(Saloon entity) {
        return repository.save(entity);
    }

    @Override
    public Saloon update(Long id, Saloon entity) {
        Saloon existingSaloon = getById(id);
        if (existingSaloon != null) {
            existingSaloon.setCapacity(entity.getCapacity());
            existingSaloon.setAvailable(entity.getAvailable());
            return repository.save(existingSaloon);
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
    public Long getCapacity(Long id){
        Saloon saloon=getById(id);
        return saloon.getCapacity();
    }
}
