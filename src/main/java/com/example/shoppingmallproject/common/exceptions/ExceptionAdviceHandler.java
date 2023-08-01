package com.example.shoppingmallproject.common.exceptions;


import com.example.shoppingmallproject.common.exceptions.dto.ExceptionResponseDto;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class ExceptionAdviceHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ExceptionResponseDto> handleCustomException(CustomException e){
        return ExceptionResponseDto.toResponseEntity(e.getErrorCode());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoSuchElementException.class)
    public ExceptionResponseDto handleNoSuchElementException(NoSuchElementException e) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ExceptionResponseDto(status.value(), status.name(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ExceptionResponseDto handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        HttpStatus status = HttpStatus.CONFLICT;

        return new ExceptionResponseDto(status.value(), status.name(), e.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponseDto handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ExceptionResponseDto(status.value(), status.name(), e.getBindingResult().getFieldErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException.class)
    public ExceptionResponseDto handleIllegalArgumentException(IllegalArgumentException e){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ExceptionResponseDto(status.value(), status.name(), e.getMessage());
    }

}
