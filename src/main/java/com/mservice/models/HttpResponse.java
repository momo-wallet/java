package com.mservice.models;

import okhttp3.Headers;

public class HttpResponse {

    int status;
    String data;
    Headers headers;

    public HttpResponse(int status, String data, Headers headers) {
        this.status = status;
        this.data = data;
        this.headers = headers;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public String toString() {
        return "HttpStatusCode:: " + this.status + ", ResponseBody:: " + this.data;
    }

}
