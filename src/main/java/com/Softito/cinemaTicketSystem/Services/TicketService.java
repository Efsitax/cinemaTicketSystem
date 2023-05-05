package com.Softito.cinemaTicketSystem.Services;

import com.Softito.cinemaTicketSystem.Model.Ticket;
import com.Softito.cinemaTicketSystem.Repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
@Service
public class TicketService implements IBaseService<Ticket> {

    @Autowired
    private TicketRepository repository;
    @Override
    public List<Ticket> getAll() {
        return repository.findAll();
    }

    @Override
    public Ticket getById(Long id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public Ticket create(Ticket entity) {
        return repository.save(entity);
    }

    @Override
    public Ticket update(Long id, Ticket entity) {
        Ticket existingTicket = getById(id);
        if (existingTicket != null) {
            existingTicket.setUser(entity.getUser());
            existingTicket.setSession(entity.getSession());
            existingTicket.setSeatNum(entity.getSeatNum());
            return repository.save(existingTicket);
        }
        return null;
    }
    @Override
    public boolean delete(Long id) {
        if(getById(id)!=null){
            repository.deleteById(id);
            return true;
        }
        else{
            return false;
        }
    }

    public List<Ticket> getTicketsBySessionId(Long sessionId) {
        return repository.findAllBySessionSessionId(sessionId);
    }
    public List<Long> getSeatNums(Long id){
        List<Ticket> tickets = getTicketsBySessionId(id);
        List<Long> seatNums = new ArrayList<>();
        for(Ticket ticket:tickets){
            seatNums.add(ticket.getSeatNum());
        }
        return seatNums;
    }
}
