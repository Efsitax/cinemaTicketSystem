package com.Softito.cinemaTicketSystem.Repository;

import com.Softito.cinemaTicketSystem.Model.Admin;
import com.Softito.cinemaTicketSystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin,Long> {
    Admin findByEmail(String email);
}
