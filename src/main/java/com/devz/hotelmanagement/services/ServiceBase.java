package com.devz.hotelmanagement.services;

import java.util.List;

public interface ServiceBase<E> {

    List<E> findAll();

    E findById(int id);

    E findByCode(String code);

    E create(E entity);

    E update(E entity);

}
