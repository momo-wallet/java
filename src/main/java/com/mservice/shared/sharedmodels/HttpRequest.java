package com.mservice.shared.sharedmodels;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;

import java.io.IOException;

public class HttpRequest {
    Request okRequest;

    public HttpRequest(String method, String endpoint, String payload, String contentType) {
        this.okRequest = createRequest(method, endpoint, payload, contentType);
    }

    private static Request createRequest(String method, String endpoint, String payload, String contentType) {
        RequestBody body = RequestBody.create(MediaType.get(contentType), payload);
        return new Request.Builder()
                .method(method, body)
                .url(endpoint)
                .build();
    }

    public Request getOkRequest() {
        return this.okRequest;
    }

    public void setOkRequest(Request okRequest) {
        this.okRequest = okRequest;
    }

    public HttpUrl getEndpoint() {
        return this.getOkRequest().url();
    }

    public String getMethod() {
        return this.getOkRequest().method();
    }

    public RequestBody getBody() {
        return this.getOkRequest().body();
    }

    public String getBodyAsString() throws IOException {
        Buffer buffer = new Buffer();
        RequestBody body = this.getBody();
        body.writeTo(buffer);
        return buffer.readUtf8();
    }

}
