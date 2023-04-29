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
    public Role getRoleById(@PathVariable Long role_id) {
        return service.getById(role_id);
    }//id==role_id oldu

    @PostMapping("/add")
    public Role createRole(@RequestBody Role entity) {
        return service.create(entity);
    }
    @DeleteMapping("/delete/{id}")
    public Boolean deleteRole(@PathVariable Long role_id) {
        return service.delete(role_id);
    }//id==role_id oldu

    @PutMapping("/update/{id}")
    public Role updateRole(@PathVariable Long role_id, @RequestBody Role entity) {
        return service.update(role_id, entity);
    }//id==role_id oldu
}
