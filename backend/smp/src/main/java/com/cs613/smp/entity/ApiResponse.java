package com.cs613.smp.entity;

public class ApiResponse<T> {
    private Boolean success;
    private T content;
    private String msg;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ApiResponse(Boolean success, T content, String msg) {
        this.success = success;
        this.content = content;
        this.msg = msg;
    }

    public static <T> ApiResponse<T> ok(T content) {
        return new ApiResponse<>(true, content, "success");
    }

    public static <T> ApiResponse<T> err(String msg) {
        return new ApiResponse<>(false, null, msg);
    }
}
