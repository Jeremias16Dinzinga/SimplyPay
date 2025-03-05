package com.sweet_company.simplyPay.config;

import com.sweet_company.simplyPay.dto.ExceptionDto;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

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

    
}
