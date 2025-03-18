package com.sweet_company.simplyPay.config;

import com.sweet_company.simplyPay.dto.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerExceptionHandler extends Exception {

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity threatDuplicateEntityException(DataIntegrityViolationException exception){
        ExceptionDto exceptionDto = new ExceptionDto("Os dados inserdo, já foram utilizados","400");
        return ResponseEntity.badRequest().body(exceptionDto);
    }
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity threat404(EntityNotFoundException exception){
        return ResponseEntity.notFound().build();
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity threatGeneralException(Exception exception){
        ExceptionDto exceptionDto = new ExceptionDto(exception.getMessage(),"500");
        return ResponseEntity.internalServerError().body(exceptionDto);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(ConstraintViolationException ex) {

        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();

        Map<String, String> errors = violations.stream()
                .collect(Collectors.toMap(
                        violation -> violation.getPropertyPath().toString(),
                        ConstraintViolation::getMessage
                ));

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Validation failed for the following fields.");
        response.put("errors", errors);
        response.put("statusCode", HttpStatus.BAD_REQUEST.value());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    
}
