package com.Softito.cinemaTicketSystem.Model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="user_id")
    private Long userId;
    @Column(name = "name")
    private String name;

    @Column(name = "surname")
    private String surname;

    @Column(name = "email",unique=true, nullable=false)
    private String email;

    @Column(name = "password",nullable=false)
    private String password;

    @Column(name = "photo", columnDefinition = "image")
    private byte[] photo;

    @ManyToOne
    @JoinColumn(name = "role_id",referencedColumnName = "role_id")
    private Role role;

    @Column(name = "balance")
    private Long balance;
    @Column(name = "token")
    private String token;
    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "created_at", columnDefinition = "smalldatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(name = "updated_at", columnDefinition = "smalldatetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;

    @JsonCreator
    public User(@JsonProperty("user_id") Long id) {
        this.userId = id;
    }
    public User(){

    }

}
