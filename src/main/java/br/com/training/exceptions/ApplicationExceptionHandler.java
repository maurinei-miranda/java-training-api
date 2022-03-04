package br.com.training.exceptions;

import org.hibernate.exception.ConstraintViolationException;
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

import javax.servlet.http.HttpServletRequest;
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
        List<String> errors = new ArrayList<String>();
        final String cpfAlreadyExist = "PUBLIC.UK_2QV8VMK5WXU215BEVLI5DERQ_INDEX_2 ON PUBLIC.USER(CPF)";
        final String emailAlreadyExist = "PUBLIC.UK_OB8KQYQQGMEFL0ACO34AKDTPE_INDEX_2 ON PUBLIC.USER(EMAIL)";
        final String vaccineAlreadyExist = "PUBLIC.UK_I7TJE2XF0KSD3MDASOXQ6QKFB_INDEX_3 ON PUBLIC.VACCINE(NAME)";

        if (ex.getConstraintName().contains(cpfAlreadyExist)) {
            errors.add(cpfAlreadyExistMessage);
        } else if (ex.getConstraintName().contains(emailAlreadyExist)) {
            errors.add(emailErrorMessage);
        } else if (ex.getConstraintName().contains(vaccineAlreadyExist)) {
            errors.add(vaccineAlreadyExistMessage);
        } else {
            errors.add(ex.getConstraintName());
        }

        ApiError apiError = new ApiError(HttpStatus.CONFLICT, errors, System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(apiError);
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<ApiError> entityNotFound(NoSuchElementException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getMessage(), System.currentTimeMillis());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<String>();
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
