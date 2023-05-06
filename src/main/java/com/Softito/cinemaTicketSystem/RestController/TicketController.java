package com.Softito.cinemaTicketSystem.RestController;

import com.Softito.cinemaTicketSystem.Model.Ticket;
import com.Softito.cinemaTicketSystem.Services.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/tickets")
public class TicketController {
    @Autowired
    private TicketService service ;

    @GetMapping("")
    public List<Ticket> getAllTicket() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Ticket getTicketById(@PathVariable Long id) {
        return service.getById(id);
    }
    @GetMapping("/seat-nums/{id}")
    public List<Long> getSeatNums(@PathVariable Long id) {
        return service.getSeatNums(id);
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
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket entity) {
        return service.update(id, entity);
    }
}
