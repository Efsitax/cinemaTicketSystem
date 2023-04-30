package com.Softito.cinemaTicketSystem.RestController;

import com.Softito.cinemaTicketSystem.Model.Admin;
import com.Softito.cinemaTicketSystem.Services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminService service ;

    @GetMapping("")
    public List<Admin> getAllAdmins() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Admin getAdminById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public Admin createAdmin(@RequestBody Admin entity) {
        return service.create(entity);
    }
    @DeleteMapping("/delete/{id}")
    public Boolean deleteAdmin(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/update/{id}")
    public Admin updateAdmin(@PathVariable Long id, @RequestBody Admin entity) {
        return service.update(id, entity);
    }
}