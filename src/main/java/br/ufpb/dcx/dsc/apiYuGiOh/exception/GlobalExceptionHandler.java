package br.ufpb.dcx.dsc.apiYuGiOh.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({
            CardNotAssociatedWithDeckException.class,
            PhotoNotAssociatedWithCardException.class
    })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleAssociationExceptions(RuntimeException ex) {
        return new ErrorResponse
                .Builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(List.of(ex.getMessage()))
                .build();
    }

    @ExceptionHandler({
            UserAlreadyExistsException.class,
            PhotoAlreadyExistsException.class,
            DeckAlreadyExistsException.class
    })
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleUserAlreadyExistsException(RuntimeException ex) {
        return new ErrorResponse
                .Builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.CONFLICT.value())
                .status(HttpStatus.CONFLICT.name())
                .errors(List.of(ex.getMessage()))
                .build();
    }

    @ExceptionHandler({
            CardNotFoundException.class,
            DeckNotFoundException.class,
            PhotoNotFoundException.class,
            UserNotFoundException.class,
            UsernameNotFoundException.class
    })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(RuntimeException ex) {
        return new ErrorResponse
                .Builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.NOT_FOUND.value())
                .status(HttpStatus.NOT_FOUND.name())
                .errors(List.of(ex.getMessage()))
                .build();
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
        List<String> errorList = ex.getConstraintViolations()
                .stream()
                .map(error -> error.getPropertyPath() + ": " + error.getMessage())
                .collect(Collectors.toList());
        ErrorResponse error = new ErrorResponse
                .Builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(errorList)
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleArgumentNotValidException(MethodArgumentNotValidException ex) {
        List<String> errorList = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.toList());
        ErrorResponse error = new ErrorResponse
                .Builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.BAD_REQUEST.value())
                .status(HttpStatus.BAD_REQUEST.name())
                .errors(errorList)
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> genericException(Exception ex) {
        ErrorResponse error = new ErrorResponse
                .Builder()
                .timestamp(LocalDateTime.now())
                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .errors(List.of(ex.getMessage()))
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
