package br.com.training.exceptions;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    public static String cpfAlreadyExistMessage = "cpf already exist";
    public static String emailErrorMessage = "email already exist";
    public static String cpfNotFoundMessage = "cpf not found: ";
    public static String vaccineAlreadyExistMessage = "vaccine already exist";

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiError> resourceViolation(ConstraintViolationException ex) {
        List<String> errors = new ArrayList<>();
        final String cpfAlreadyExist = "user.UK_2qv8vmk5wxu215bevli5derq";
        final String emailAlreadyExist = "user.UK_ob8kqyqqgmefl0aco34akdtpe";
        final String vaccineAlreadyExist = "vaccine.UK_i7tje2xf0ksd3mdasoxq6qkfb";

        if (ex.getConstraintName().contains(cpfAlreadyExist)) {
            errors.add(cpfAlreadyExistMessage);
        } else if (ex.getConstraintName().contains(emailAlreadyExist)) {
            errors.add(emailErrorMessage);
        } else if (ex.getConstraintName().contains(vaccineAlreadyExist)) {
            errors.add(vaccineAlreadyExistMessage);
        } else {
            errors.add("Exception not mapped");
            errors.add(ex.getConstraintName());
        }

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, errors, System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> entityNotFound(NoSuchElementException ex) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @ExceptionHandler(VaccineRestrictions.class)
    public ResponseEntity<ApiError> restrictionFound(VaccineRestrictions ex){
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

//    @ExceptionHandler(DataIntegrityViolationException.class)
//    public ResponseEntity<ApiError> duplicateEntry(DataIntegrityViolationException ex, String attr){
//        ApiError apiError = new ApiError(HttpStatus.CONFLICT, "Entrada duplicada", System.currentTimeMillis());
//        ex.getCause().getMessage();
//        String detailMessage = ex.getCause().getCause().getMessage();
//        if (detailMessage.contains("Duplicate entry for key user")) {
//
//        }
//        return ResponseEntity.status(apiError.getStatus()).body(apiError);
//    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
        }

        ApiError apiError =
                new ApiError(HttpStatus.BAD_REQUEST, errors, System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

}
