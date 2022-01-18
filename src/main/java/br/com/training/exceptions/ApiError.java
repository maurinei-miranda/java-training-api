package br.com.training.exceptions;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

public class ApiError {

    private HttpStatus status;
    private List<String> errors;
    private Long timestamp;

    public ApiError(HttpStatus status, List<String> errors, Long timestamp) {
        super();
        this.status = status;
        this.errors = errors;
        this.timestamp = timestamp;

    }

    public ApiError(HttpStatus status, String error, Long timestamp) {
        super();
        this.status = status;
        this.timestamp = timestamp;
        errors = Arrays.asList(error);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
