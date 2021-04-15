package com.swarts.sb.account.controller;


import com.swarts.sb.account.dto.ErrorDto;
import com.swarts.sb.account.exception.AccountNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class AccountExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = AccountNotFoundException.class)
    protected ResponseEntity<ErrorDto> handleAccountNotFoundException(AccountNotFoundException ex) {

        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorDto error = new ErrorDto(status.value(),
                status.getReasonPhrase(),
                ex.getMessage());

        logger.info(ex.getMessage());
        return ResponseEntity.status(status).body(error);
    }
}
