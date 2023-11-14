package com.noproject.bookstore.api.query;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

public class HandlerCollection {

    private final Map<String, Function<?, ?>> handlers;

    public HandlerCollection() {
        handlers = new LinkedHashMap<>();
    }

    HandlerCollection concat(HandlerCollection handlerCollection) {
        this.handlers.putAll(handlerCollection.handlers);
        return this;
    }

    public <T> void addHandler(String key, Function<T, ?> handler) {
        this.handlers.put(key, handler);
    }

    public Function<?, ?> getHandler(String key) {
        return handlers.get(key);
    }

}
