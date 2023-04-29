package com.Softito.cinemaTicketSystem.Services;

import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface IBaseService<T>{
    List<T> getAll();
    T getById(Long id);
    T create(T entity);
    T update(Long id, T entity);
    boolean delete(Long id);
}
