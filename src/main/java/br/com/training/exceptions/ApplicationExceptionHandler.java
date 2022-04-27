package br.com.training.exceptions;

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

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ApiError> resourceViolation(DataIntegrityViolationException ex) {
        List<String> errors = new ArrayList<>();
        final String cpfConstraint = "user.uc_user_cpf";
        final String emailConstraint = "user.uc_user_email";
        final String vaccineConstraint = "vaccine.uc_vaccine_name";

        if (ex.getMostSpecificCause().getMessage().contains(cpfConstraint)) {
            errors.add(cpfAlreadyExistMessage);
        } else if (ex.getMostSpecificCause().getMessage().contains(emailConstraint)) {
            errors.add(emailErrorMessage);
        } else if (ex.getMostSpecificCause().getMessage().contains(vaccineConstraint)) {
            errors.add(vaccineAlreadyExistMessage);
        } else {
            errors.add("Exception not mapped");
            errors.add(ex.getMostSpecificCause().getMessage());
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
    public ResponseEntity<ApiError> restrictionFound(VaccineRestrictions ex) {
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }

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
