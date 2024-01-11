package com.example.base.web.dto;


import com.example.base.common.domain.BusinessCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ApiResponse<T> {
    private String code;
    private String message;
    private T data;

    public ApiResponse(BusinessCode businessCode) {
        this.code = businessCode.getCode();
        this.message = businessCode.getDescription();
    }

    public ApiResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
