package com.noproject.bookstore.mapper;

import java.util.Collection;

public abstract class Mapper<S, D> {

    private final Class<D> destinationClass;

    protected Mapper(Class<D> destinationClass) {
        this.destinationClass = destinationClass;
    }

    protected abstract void handleMapping(S provider, D consumer);

    public D map(S obj) {
        try {
            D instance = destinationClass.getDeclaredConstructor().newInstance();
            handleMapping(obj, instance);
            return instance;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <R extends Collection<D>> R map(Collection<S> collection, R collectionInstance) {
        for (S provider : collection) {
            collectionInstance.add(map(provider));
        }
        return collectionInstance;
    }

}
