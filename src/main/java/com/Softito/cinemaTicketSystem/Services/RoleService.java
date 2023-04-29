package com.Softito.cinemaTicketSystem.Services;

import com.Softito.cinemaTicketSystem.Model.Role;
import com.Softito.cinemaTicketSystem.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class RoleService implements IBaseService<Role> {
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Role getById(Long role_id) {
        return roleRepository.findById(role_id).orElse(null);
    }//id==role_id

    @Override
    public Role create(Role entity) {
        return roleRepository.save(entity);
    }

    @Override
    public Role update(Long role_id, Role entity) {//id==role_id oldu
        Role existingRole = getById(role_id);//id=role_id oldu
        if (existingRole != null) {
            existingRole.setName(entity.getName());
            return roleRepository.save(existingRole);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {//id==role_id
        if(getById(id)!=null){
            roleRepository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }
}
