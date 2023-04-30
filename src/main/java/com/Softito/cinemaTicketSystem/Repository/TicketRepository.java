package com.Softito.cinemaTicketSystem.Repository;

import com.Softito.cinemaTicketSystem.Model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
}