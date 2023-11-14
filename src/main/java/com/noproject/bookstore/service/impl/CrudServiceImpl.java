package com.noproject.bookstore.service.impl;

import com.noproject.bookstore.service.CrudService;
import com.noproject.bookstore.service.QueryService;
import com.noproject.bookstore.service.RemovalService;
import com.noproject.bookstore.service.SaveChangesService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public class CrudServiceImpl<E, ID> implements CrudService<E, ID> {

    private final QueryService<E, ID> queryService;
    private final SaveChangesService<E> saveChangesService;
    private final RemovalService<E, ID> removalService;

    public CrudServiceImpl(JpaRepository<E, ID> repository) {
        this.queryService = new QueryServiceImpl<>(repository);
        this.saveChangesService = new SaveChangesServiceImpl<>(repository);
        this.removalService = new RemovalServiceImpl<>(repository);
    }

    @Override
    public List<E> findAll() {
        return queryService.findAll();
    }

    @Override
    public Optional<E> findById(ID id) {
        return queryService.findById(id);
    }

    @Override
    public Boolean delete(E entity) {
        return removalService.delete(entity);
    }

    @Override
    public Boolean deleteById(ID id) {
        return removalService.deleteById(id);
    }

    @Override
    public Optional<E> save(E obj) {
        return saveChangesService.save(obj);
    }

}
