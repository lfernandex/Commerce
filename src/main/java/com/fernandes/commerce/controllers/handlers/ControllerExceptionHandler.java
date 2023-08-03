package com.fernandes.commerce.controllers.handlers;

import java.time.Instant;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.fernandes.commerce.dto.CustomError;
import com.fernandes.commerce.services.exceptions.DatabaseException;
import com.fernandes.commerce.services.exceptions.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;

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
}
