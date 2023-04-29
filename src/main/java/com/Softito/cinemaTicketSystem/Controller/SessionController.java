package com.Softito.cinemaTicketSystem.Controller;

import com.Softito.cinemaTicketSystem.Model.Session;
import com.Softito.cinemaTicketSystem.Services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sessions")
public class SessionController {
    @Autowired
    private SessionService service ;

    @GetMapping("")
    public List<Session> getAllSession() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Session getSessionById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping("/add")
    public Session createSession(@RequestBody Session entity) {
        return service.create(entity);
    }
    @DeleteMapping("/delete/{id}")
    public Boolean deleteSession(@PathVariable Long id) {
        return service.delete(id);
    }

    @PutMapping("/update/{id}")
    public Session updateSession(@PathVariable Long id, @RequestBody Session entity) {
        return service.update(id, entity);
    }
}