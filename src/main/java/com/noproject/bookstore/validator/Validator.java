package com.noproject.bookstore.validator;

import lombok.Getter;

import java.util.Map;
import java.util.Objects;

public class Validator<T> {

    @FunctionalInterface
    public interface Tester<T> {
        boolean test(T obj);
    }

    @Getter
    public static final class Result {

        private enum Case {
            SUCCESS, FAIL
        }

        private final Case value;

        private String reason = null;
        private String key = null;

        private Result(Case resultCase, String key) {
            this.value = resultCase;
            if (this.value == Case.FAIL) {
                this.key = key;
                this.reason = String.format("The value of %s does not satisfy the condition.", key);
            }
        }

        public boolean isFail() {
            return value == Case.FAIL;
        }

        public boolean isSuccess() {
            return value == Case.SUCCESS;
        }

    }

    public static final String KEY_OBJECT = "object";

    private final Map<String, Tester<T>> testers;

    protected Validator(Map<String, Tester<T>> testers) {
        if (!testers.containsKey(KEY_OBJECT)) {
            testers.put(KEY_OBJECT, Objects::nonNull);
        }
        this.testers = testers;
    }

    public final Result validate(T obj) {

        if (Objects.isNull(testers)) {
            throw new UnsupportedOperationException("The tester list has not been initialized.");
        }

        for (Map.Entry<String, Tester<T>> tester : testers.entrySet()) {
            if (!tester.getValue().test(obj)) {
                return new Result(Result.Case.FAIL, tester.getKey());
            }
        }

        return new Result(Result.Case.SUCCESS, null);
    }

    public static <T> Result validate(T obj, Map<String, Tester<T>> testers) {
        return new Validator<>(testers).validate(obj);
    }

}
