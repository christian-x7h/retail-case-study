package app.retail.app;

import app.retail.model.api.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(ApplicationExceptionHandler.class);

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException e) {
        log.error("RuntimeException: {}", e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ErrorResponse> handleApplicationException(ApplicationException e) {
        log.warn("ApplicationException: {} | {}", e.getStatus(), e.getMessage(), e);
        ErrorResponse errorResponse = new ErrorResponse(e.getMessage(), e.getStatus().value());
        return ResponseEntity.status(errorResponse.getStatus()).body(errorResponse);
    }
}
