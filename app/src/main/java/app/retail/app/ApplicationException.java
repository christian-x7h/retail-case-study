package app.retail.app;

import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

    public HttpStatus status;

    public ApplicationException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

    public ApplicationException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }


    @Override
    public String toString() {
        return "ApplicationException{" +
                "message=" + getMessage() +
                "status=" + status +
                '}';
    }
}
