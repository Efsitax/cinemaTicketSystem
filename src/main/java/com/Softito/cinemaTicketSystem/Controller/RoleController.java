package com.Softito.cinemaTicketSystem.Controller;

import com.Softito.cinemaTicketSystem.Model.Role;
import com.Softito.cinemaTicketSystem.Services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService service ;

    @GetMapping("")
    public List<Role> getAllRoles() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public Role createRole(@RequestBody Role role) {
        return service.create(role);
    }
    @DeleteMapping("/delete/{id}")
    public Boolean deleteRole(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/update/{id}")
    public Role updateRole(@PathVariable Long id, @RequestBody Role role) {
        return service.update(id, role);
    }
}
