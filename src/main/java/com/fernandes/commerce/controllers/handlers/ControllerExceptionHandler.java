package com.fernandes.commerce.controllers.handlers;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fernandes.commerce.dto.CustomError;
import com.fernandes.commerce.dto.ValidationError;
import com.fernandes.commerce.services.exceptions.DatabaseException;
import com.fernandes.commerce.services.exceptions.ForbiddenException;
import com.fernandes.commerce.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ControllerExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<CustomError> resourceNotFound(ResourceNotFoundException e, HttpServletRequest request){

            HttpStatus status = HttpStatus.NOT_FOUND;

            CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

            return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(DatabaseException.class)
        public ResponseEntity<CustomError> databaseException(DatabaseException e, HttpServletRequest request){

            HttpStatus status = HttpStatus.BAD_REQUEST;

            CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

            return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        public ResponseEntity<CustomError> methodArgumentNotValidation(MethodArgumentNotValidException e, HttpServletRequest request){

            HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;

            ValidationError err = new ValidationError(Instant.now(), status.value(), "dados invalidos", request.getRequestURI());

            for(FieldError f : e.getBindingResult().getFieldErrors()) {
                err.addError(f.getField(), f.getDefaultMessage());
            }

            return ResponseEntity.status(status).body(err);
        }

        @ExceptionHandler(ForbiddenException.class)
        public ResponseEntity<CustomError> forbidden(ForbiddenException e, HttpServletRequest request){

            HttpStatus status = HttpStatus.FORBIDDEN;

            CustomError err = new CustomError(Instant.now(), status.value(), e.getMessage(), request.getRequestURI());

            return ResponseEntity.status(status).body(err);
        }
}
