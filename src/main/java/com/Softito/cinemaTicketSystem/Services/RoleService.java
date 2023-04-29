package com.Softito.cinemaTicketSystem.Services;

import com.Softito.cinemaTicketSystem.Model.Role;
import com.Softito.cinemaTicketSystem.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService implements IBaseService<Role> {
    @Autowired
    private RoleRepository repository;

    @Override
    public List<Role> getAll() {
        return repository.findAll();
    }

    @Override
    public Role getById(Long role_id) {
        return repository.findById(role_id).orElse(null);
    }//id==role_id

    @Override
    public Role create(Role entity) {
        return repository.save(entity);
    }

    @Override
    public Role update(Long role_id, Role entity) {//id==role_id oldu
        Role existingRole = getById(role_id);//id=role_id oldu
        if (existingRole != null) {
            existingRole.setName(entity.getName());
            return repository.save(existingRole);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {//id==role_id
        if(getById(id)!=null){
            repository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
