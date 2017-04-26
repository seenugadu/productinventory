package com.productinventory.intg.exception.dto;

/**
 * Created by associate on 4/20/17.
 */
public class Error {

    private String code;
    private String message;

    public Error() {
    }

    public Error(String var1, String var2) {
        this.code = var1;
        this.message = var2;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String var1) {
        this.code = var1;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String var1) {
        this.message = var1;
    }
}
