package com.noproject.bookstore.api.query;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

public class ApiRequest<T> {

    private List<String> query;

    private HandlerCollection cachedHandlerCollection = null;

    public void setQuery(String... fields) {
        this.query = new LinkedList<>(Arrays.asList(fields));
    }

    @SuppressWarnings("unchecked")
    public ApiResponse<List<?>> getResponseArray(ResponseInformation information, List<T> listDataBindings) {

        /*
            Giả khối code get GenericType có Big O là O(1)
         */
        ParameterizedType pt = (ParameterizedType) listDataBindings.getClass().getGenericSuperclass();
        Type genericType = pt.getActualTypeArguments()[0];
        if (!genericType.getClass().equals(Class.class)) {
            throw new RuntimeException();
        }

        if (Objects.isNull(cachedHandlerCollection)) {
            cachedHandlerCollection = RequestHandler.getHandlers((Class<T>) genericType);
        }

        final List<Map<String, Object>> responseArray = new ArrayList<>();

        final int listSize = listDataBindings.size(), querySize = query.size();

        int listIndex = 0, queryIndex = 0;
        T dataBinding = listDataBindings.get(listIndex);
        Map<String, Object> responseObject = new LinkedHashMap<>();
        /*
            logic này chạy querySize * listSize lần nhưng Big O là O(n)
         */
        while (listIndex < listSize) {

            if (queryIndex < querySize) {

                setObjectValue(queryIndex, cachedHandlerCollection, responseObject, dataBinding);
                queryIndex++;

            } else {

                responseArray.add(responseObject);

                listIndex++;
                if (listIndex < listSize) {
                    queryIndex = 0;
                    dataBinding = listDataBindings.get(listIndex);
                    responseObject = new LinkedHashMap<>();
                }

            }

        }

        return new ApiResponse<>(information, responseArray);
    }

    public ApiResponse<?> getResponseObject(ResponseInformation information, T dataBinding) {

        if (Objects.isNull(cachedHandlerCollection)) {
            cachedHandlerCollection = RequestHandler.getHandlers(dataBinding.getClass());
        }

        final Map<String, Object> responseObject = new LinkedHashMap<>();

        final int queryLength = query.size();
        for (int i = 0; i < queryLength; i++) {
            setObjectValue(i, cachedHandlerCollection, responseObject, dataBinding);
        }

        return new ApiResponse<>(information, responseObject);
    }

    @SuppressWarnings("unchecked")
    private void setObjectValue(
            int queryIndex,
            HandlerCollection handlers,
            Map<String, Object> object,
            T dataBiding) {

        // O(1) do get của ArrayList có Big O = 1
        String key = query.get(queryIndex);

        // O(1) do get của các Implementation của Map là 1
        Function<T, ?> handler = (Function<T, ?>) handlers.getHandler(key);

        // O(1) do put của các Implementation của Map là 1
        object.put(key, handler.apply(dataBiding));

    }

}
