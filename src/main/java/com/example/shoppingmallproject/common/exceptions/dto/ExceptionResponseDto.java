package com.example.shoppingmallproject.common.exceptions.dto;

import com.example.shoppingmallproject.common.exceptions.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

@Getter
public class ExceptionResponseDto {

    private int status;
    private String error;
    private String message;


    /**
     * 생성자
     *
     * @param message 오류 메세지
     * @param status  오류 상태 코드
     */
    @Builder
    public ExceptionResponseDto(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
    }

    public static ResponseEntity<ExceptionResponseDto> toResponseEntity(ErrorCode errorCode){
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(ExceptionResponseDto.builder()
                        .status(errorCode.getHttpStatus().value())
                        .error(errorCode.getHttpStatus().name())
                        .message(errorCode.getMessage())
                        .build());
    }
}
