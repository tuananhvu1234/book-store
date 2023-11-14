package com.noproject.bookstore.api.query;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class RequestHandler {

    private static final Map<Class<?>, HandlerCollection> collection;

    static {
        collection = Collections.synchronizedMap(new LinkedHashMap<>());
    }

    private RequestHandler() {
    }

    public static HandlerCollection add(Class<?> forClass, HandlerCollection handlerCollection) {
        if (collection.containsKey(forClass)) {
            collection.get(forClass).concat(handlerCollection);
        } else {
            collection.put(
                    forClass,
                    new HandlerCollection().concat(handlerCollection)
            );
        }
        return collection.get(forClass);
    }

    public static HandlerCollection getHandlers(Class<?> clazz) {
        return collection.get(clazz);
    }

}
