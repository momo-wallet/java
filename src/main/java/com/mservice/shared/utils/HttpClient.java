package com.mservice.shared.utils;

import com.mservice.shared.exception.MoMoException;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClient {

    @SuppressWarnings("deprecation")
    public static HttpResponse post(HttpRequest httpRequest) throws MoMoException {

        try {
            HttpResponse response = new HttpResponse();

            HttpPost post = new HttpPost(httpRequest.getUrl());
            Headers headers = httpRequest.getHeaders();

            StringEntity entity = new StringEntity(httpRequest.getData(), "UTF-8");
            entity.setContentEncoding("UTF-8");
            post.setEntity(entity);
            headers.getHeaders().forEach((k, v) -> post.addHeader(k, v));
            org.apache.http.client.HttpClient httpClient = HttpClientBuilder.create().build();

            org.apache.http.HttpResponse httpResponse = httpClient.execute(post);

            byte[] bytes = IOUtils.toByteArray(httpResponse.getEntity().getContent());

            String responseStr = new String(bytes, "UTF-8");

            response.setStatus(httpResponse.getStatusLine().getStatusCode());
            response.setData(responseStr);

            return response;
        } catch (Exception e) {
            e.printStackTrace();
            throw new MoMoException(e);
        }
    }
}
