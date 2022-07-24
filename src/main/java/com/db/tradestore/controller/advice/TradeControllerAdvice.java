package com.db.tradestore.controller.advice;

import com.db.tradestore.exception.InvalidTradeException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@ControllerAdvice
public class TradeControllerAdvice {

    @ExceptionHandler(InvalidTradeException.class)
    public ResponseEntity<?> handleInvalidTradeException(final InvalidTradeException ex) {
        final String message =
                Optional.of(ex.getMessage()).orElse(ex.getClass().getSimpleName());
        return new ResponseEntity<>(message, HttpStatus.NOT_ACCEPTABLE);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        final String message = "Malformed JSON request";
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }
}
