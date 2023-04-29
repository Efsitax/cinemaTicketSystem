package com.Softito.cinemaTicketSystem.Controller;

import com.Softito.cinemaTicketSystem.Model.Saloon;
import com.Softito.cinemaTicketSystem.Services.SaloonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/saloons")
public class SaloonController {
    @Autowired
    private SaloonService service ;

    @GetMapping("")
    public List<Saloon> getAllSaloon() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Saloon getSaloonById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public Saloon createSaloon(@RequestBody Saloon entity) {
        return service.create(entity);
    }
    @DeleteMapping("/delete/{id}")
    public Boolean deleteSaloon(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/update/{id}")
    public Saloon updateSaloon(@PathVariable Long id, @RequestBody Saloon entity) {
        return service.update(id, entity);
    }
}