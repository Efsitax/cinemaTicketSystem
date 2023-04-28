package com.Softito.cinemaTicketSystem.Services;

import java.util.List;

public interface IBaseService<T>{
    List<T> getAll();
    T getById(Long id);
    T create(T entity);
    T update(Long id, T entity);
    boolean delete(Long id);
}
