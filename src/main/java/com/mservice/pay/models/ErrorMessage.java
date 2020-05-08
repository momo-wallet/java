package com.mservice.pay.models;

import com.google.gson.JsonObject;

public class ErrorMessage {

    protected Integer status;
    protected String message;
    protected JsonObject error;

    public ErrorMessage(Integer status, String message, JsonObject error) {
        this.status = status;
        this.message = message;
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JsonObject getError() {
        return error;
    }

    public void setError(JsonObject error) {
        this.error = error;
    }
}