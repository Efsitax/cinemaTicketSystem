package com.Softito.cinemaTicketSystem.RestController;

import com.Softito.cinemaTicketSystem.Model.User;
import com.Softito.cinemaTicketSystem.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService service ;

    @GetMapping("")
    public List<User> getAllUser() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return service.getById(id);
    }


    @PostMapping("/add")
    public User createUser(@RequestBody User entity) {
        return service.create(entity);
    }
    @DeleteMapping("/delete/{id}")
    public Boolean deleteUser(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/update/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User entity) {
        return service.update(id, entity);
    }
}
