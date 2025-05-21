package com.ascendion.library.exception;

import com.ascendion.library.dto.response.ErrorResponse;
import com.ascendion.library.dto.response.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**+
     * Handler method for API validation Errors
     * @param exception - MethodArgumentNotValidException
     * @return ValidationErrorResponse Object containing error code and field error list.
     */
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ValidationErrorResponse> handleValidationErrors(MethodArgumentNotValidException exception) {
        log.error("MethodArgumentNotValidException", exception);
        // extract and group errors messages
        Map<String, List<String>> errors = exception.getBindingResult().getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(FieldError::getDefaultMessage, Collectors.toList())
                ));

        return ResponseEntity.badRequest()
                .body(new ValidationErrorResponse("API Validation failed", errors));
    }

    /**+
     * handler method for IllegalArgumentException Exceptions
     * @param exception - IllegalArgumentException class
     * @return ErrorResponse containing error details
     */

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException(IllegalArgumentException exception) {
        log.error("IllegalArgumentException Exception", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("BAD_REQUEST", exception.getMessage()));
    }

    /**+
     * handler method for RecordAlreadyExistException Exceptions
     * @param exception - RecordAlreadyExistException class
     * @return ErrorResponse containing error details
     */

    @ExceptionHandler(RecordAlreadyExistException.class)
    public ResponseEntity<ErrorResponse> handleRecordAlreadyExistException(RecordAlreadyExistException exception) {
        log.error("RecordAlreadyExistException Exception", exception);
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ErrorResponse("RECORD_ALREADY_EXIST", exception.getMessage()));
    }

    /**+
     * handler method for ResourceNotFoundException Exceptions
     * @param exception - ResourceNotFoundException class
     * @return ErrorResponse containing error details
     */

    @ExceptionHandler(RecordNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleRecordNotFoundException(RecordNotFoundException exception) {
        log.error("RecordNotFoundException Exception", exception);
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("RECORD_NOT_FOUND", exception.getMessage()));
    }

    /**+
     * handler method for NoResourceFoundException Exceptions
     * @param exception - NoResourceFoundException class
     * @return ErrorResponse containing error details
     */

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNoResourceFoundException(NoResourceFoundException exception) {
        log.error("NoResourceFoundException Exception", exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse("RESOURCE_NOT_FOUND", exception.getMessage()));
    }

    /**+
     * handler method for global Exceptions
     * @param exception - Exception class
     * @return ErrorResponse containing error details
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGlobalException(Exception exception) {
        log.error("Common Exception", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("INTERNAL_ERROR", "Please contact Server Admin"));
    }

}
