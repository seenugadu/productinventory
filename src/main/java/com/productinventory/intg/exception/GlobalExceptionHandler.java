package com.productinventory.intg.exception;

import com.productinventory.intg.exception.dto.Error;
import com.productinventory.intg.exception.dto.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;

import java.util.Locale;

/**
 * Created by associate on 4/21/17.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    Locale currentLocale = LocaleContextHolder.getLocale();

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    public ErrorResponse processError(Exception ex) {
        Error error = new Error("9999", ex.getMessage());
        return new ErrorResponse(error);
    }

    @ExceptionHandler(RestClientException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse processError(RestClientException ex) {
        Error error = getErrorMessage("5010");
        return new ErrorResponse(error);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponse processError(HttpClientErrorException ex) {
        Error error = getErrorMessage("5010");
        return new ErrorResponse(error);
    }

    /**
     * Prepare error response with list of errors
     *
     * Read the error code and message from the message.properties file using the validation / javax contraint messages
     * These constraint messages should be a property / key name in the message.properties file.
     * @param errorCode
     * @return
     */
    public Error getErrorMessage(String errorCode) {
        Error errorObject = null;

        String errorValue = messageSource.getMessage(errorCode, null, currentLocale);
        if (!StringUtils.isEmpty(errorValue)) {
            errorObject = new Error("PRODUCT_INVENTORY_INTG_MS_" + errorCode, errorValue);
        }
        else{
            errorObject = new Error("PPRODUCT_INVENTORY_INTG_MS_4xx","Invalid request parameters - " + errorCode);
        }

        return errorObject;
    }
}
