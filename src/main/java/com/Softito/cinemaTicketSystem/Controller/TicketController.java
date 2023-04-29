package com.Softito.cinemaTicketSystem.Controller;

import com.Softito.cinemaTicketSystem.Model.Session;
import com.Softito.cinemaTicketSystem.Model.Ticket;
import com.Softito.cinemaTicketSystem.Services.SessionService;
import com.Softito.cinemaTicketSystem.Services.TicketService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class TicketController {

    private TicketService service ;

    @GetMapping("")
    public List<Ticket> getAllTicket() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public Ticket createTicket(@RequestBody Ticket entity) {
        return service.create(entity);
    }
    @DeleteMapping("/delete/{id}")
    public Boolean deleteTicket(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/update/{id}")
    public Ticket updateSession(@PathVariable Long id, @RequestBody Ticket entity) {
        return service.update(id, entity);
    }
}
