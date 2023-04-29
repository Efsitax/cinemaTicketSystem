package com.Softito.cinemaTicketSystem.Services;

import com.Softito.cinemaTicketSystem.Model.Admin;
import com.Softito.cinemaTicketSystem.Repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminService implements IBaseService<Admin> {
    @Autowired
    private AdminRepository repository;
    @Override
    public List<Admin> getAll() {
        return repository.findAll();
    }

    @Override
    public Admin getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Admin create(Admin entity) {
        return repository.save(entity);
    }

    @Override
    public Admin update(Long id, Admin entity) {
        Admin existingAdmin = getById(id);
        if (existingAdmin != null) {
            existingAdmin.setName(entity.getName());
            return repository.save(existingAdmin);
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
