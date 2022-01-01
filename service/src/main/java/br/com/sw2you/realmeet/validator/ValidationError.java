package br.com.sw2you.realmeet.validator;

import java.util.Objects;

public class ValidationError {
    private final String field;
    private final String errorCode;

    public ValidationError(String field, String errorCode) {
        this.field = field;
        this.errorCode = errorCode;
    }

    public String getField() {
        return this.field;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationError that = (ValidationError) o;
        return Objects.equals(this.field, that.field) && Objects.equals(this.errorCode, that.errorCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.field, this.errorCode);
    }

    @Override
    public String toString() {
        return "ValidationError{" + "field='" + this.field + '\'' + ", errorCode='" + this.errorCode + '\'' + '}';
    }
}
