package com.Softito.cinemaTicketSystem.Services;

import com.Softito.cinemaTicketSystem.Model.Session;
import com.Softito.cinemaTicketSystem.Model.User;
import com.Softito.cinemaTicketSystem.Repository.SessionRepository;
import com.Softito.cinemaTicketSystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Service
public class UserService implements IBaseService<User> {

    @Autowired
    private UserRepository repository;

    @Override
    public List<User> getAll() {
        return repository.findAll();
    }

    @Override
    public User getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public User create(User entity) {
        return repository.save(entity);
    }

    @Override
    public User update(Long id, User entity) {
        User existingUser = getById(id);
        if (existingUser != null) {
            existingUser.setRole(entity.getRole());
            return repository.save(existingUser);
        }
        return null;
    }

    @Override
    public boolean delete(Long id) {
        if (getById(id) != null) {
            repository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
