package com.cs613.smp.utils;

import com.cs613.smp.entity.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ApiResponse<Object> handleGeneralException(Exception ex, HttpServletRequest request) {
        String msg = ex.getClass().getName() + ":" + request.getRequestURI() + ":" + ex.getMessage();

        return ApiResponse.err(msg);
    }
}
