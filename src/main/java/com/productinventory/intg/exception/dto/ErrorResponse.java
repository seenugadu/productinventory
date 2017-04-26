package com.productinventory.intg.exception.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by associate on 4/20/17.
 */
public class ErrorResponse {

    private List<Error> errors;

    public ErrorResponse() {
    }

    public ErrorResponse(List<Error> var1) {
        this.errors = var1;
    }

    public ErrorResponse(Error var1) {
        this.errors = new ArrayList(1);
        this.errors.add(var1);
    }

    public List<Error> getErrors() {
        return this.errors;
    }

    public void setErrors(List<Error> var1) {
        this.errors = var1;
    }
}
