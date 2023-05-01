package com.Softito.cinemaTicketSystem.Repository;

import com.Softito.cinemaTicketSystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);
    User findByPassword(String password);
}
