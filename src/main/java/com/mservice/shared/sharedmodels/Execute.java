package com.mservice.shared.sharedmodels;

import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.*;

@SuppressWarnings("restriction")
public class Execute {

    public static HttpResponse sendToMoMo(String endpoint, String uri, String payload) throws MoMoException {
        Headers headers = new Headers();
        headers.put("Content-Type", "application/json")
                .put("Charset", "utf-8");

        HttpRequest httpRequest = new HttpRequest("POST", payload, endpoint + uri, headers);

        Console.debug("sendToMoMoServer::Endpoint::" + httpRequest.getUrl());
        Console.debug("sendToMoMoServer::RequestBody::" + httpRequest.getData());
        Console.debug("sendToMoMoServer::Header::" + httpRequest.getHeaders());

        HttpResponse httpResponse = HttpClient.post(httpRequest);

        Console.debug("sendToMoMoServer::ResponseStatus::" + httpResponse.getStatus());
        Console.debug("sendToMoMoServer::ResponseData::" + httpResponse.getData());

        return httpResponse;
    }
}
