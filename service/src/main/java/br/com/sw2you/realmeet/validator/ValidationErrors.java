package br.com.sw2you.realmeet.validator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.springframework.data.util.Streamable;

public class ValidationErrors implements Streamable<ValidationError> {
    private final List<ValidationError> validationErrorList;

    public ValidationErrors() {
        this.validationErrorList = new ArrayList<>();
    }

    public ValidationErrors add(String field, String errorCode) {
        return this.add(new ValidationError(field, errorCode));
    }

    public ValidationErrors add(ValidationError validationError) {
        this.validationErrorList.add(validationError);
        return this;
    }

    public ValidationError getError(int index) {
        return this.validationErrorList.get(index);
    }

    public int getNumberOfErrors() {
        return this.validationErrorList.size();
    }

    public boolean hasErrors() {
        return !this.validationErrorList.isEmpty();
    }

    @Override
    public String toString() {
        return "ValidationErrors{" + "validationErrorList=" + this.validationErrorList + '}';
    }

    @Override
    public Iterator<ValidationError> iterator() {
        return this.validationErrorList.iterator();
    }
}
