package com.mservice.shared.utils;

import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.HttpRequest;
import com.mservice.shared.sharedmodels.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;

@Slf4j
public class Execute {

    OkHttpClient client = new OkHttpClient();


    public HttpResponse sendToMoMo(String endpoint, String payload) throws MoMoException {

        try {

            HttpRequest request = new HttpRequest("POST", endpoint, payload, "application/json");

            log.debug("[HttpPostToMoMo] Endpoint:: " + request.getEndpoint() + ", RequestBody:: " + request.getBodyAsString());

            Response result = client.newCall(request.getOkRequest()).execute();
            HttpResponse response = new HttpResponse(result.code(), result.body().string(), result.headers());

            log.info("[HttpResponseFromMoMo] HttpStatusCode:: " + response.getStatus() + ", ResponseBody:: " + response.getData());

            return response;
        } catch (Exception e) {
            log.error("[RefundMoMoProcess] ", e);
        }

        return null;
    }
}