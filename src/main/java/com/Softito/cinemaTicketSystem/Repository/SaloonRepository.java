package com.Softito.cinemaTicketSystem.Repository;

import com.Softito.cinemaTicketSystem.Model.Saloon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SaloonRepository extends JpaRepository<Saloon, Long> {
}
