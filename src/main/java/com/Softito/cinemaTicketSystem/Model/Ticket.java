package com.Softito.cinemaTicketSystem.Model;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "Tickets")
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="ticket_id")
    private Long ticketId;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "session_id",referencedColumnName = "session_id")
    private Session session;

    @Column(name = "seat_num")
    private Long seatNum;
}
