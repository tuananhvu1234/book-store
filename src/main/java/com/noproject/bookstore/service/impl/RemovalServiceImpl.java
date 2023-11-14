package com.noproject.bookstore.service.impl;

import com.noproject.bookstore.exception.BadRequestException;
import com.noproject.bookstore.exception.InternalServerErrorException;
import com.noproject.bookstore.service.RemovalService;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RemovalServiceImpl<E, ID> implements RemovalService<E, ID> {

    private final JpaRepository<E, ID> repository;

    public RemovalServiceImpl(JpaRepository<E, ID> repository) {
        this.repository = repository;
    }

    @Override
    public Boolean delete(E entity) {

        if (Objects.isNull(entity)) {
            throw new BadRequestException();
        }

        try {
            repository.delete(entity);
            return true;
        } catch (OptimisticLockingFailureException e) {
            Logger.getLogger(RemovalServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new InternalServerErrorException();
        }
    }

    @Override
    public Boolean deleteById(ID id) {

        if (Objects.isNull(id)) {
            throw new BadRequestException();
        }

        try {
            repository.deleteById(id);
            return true;
        } catch (OptimisticLockingFailureException e) {
            Logger.getLogger(RemovalServiceImpl.class.getName()).log(Level.SEVERE, null, e);
            throw new InternalServerErrorException();
        }
    }

}
