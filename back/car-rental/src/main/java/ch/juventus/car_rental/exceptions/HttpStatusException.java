package ch.juventus.car_rental.exceptions;

import org.springframework.http.HttpStatus;

public class HttpStatusException extends RuntimeException {
    private final HttpStatus status;

    public HttpStatusException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

}
