package com.mservice.shared.utils;

import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.sharedmodels.HttpRequest;
import com.mservice.shared.sharedmodels.HttpResponse;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Execute {

    public final Logger logger = LoggerFactory.getLogger(getClass());

    OkHttpClient client = new OkHttpClient();

    public HttpResponse sendToMoMo(String endpoint, String payload) throws MoMoException {

        try {

            HttpRequest request = new HttpRequest("POST", endpoint, payload, "application/json");

            logger.debug("[HttpPostToMoMo] Endpoint:: " + request.getEndpoint() + ", RequestBody:: " + request.getBodyAsString());

            Response result = client.newCall(request.getOkRequest()).execute();
            HttpResponse response = new HttpResponse(result.code(), result.body().string(), result.headers());

            logger.info("[HttpResponseFromMoMo] HttpStatusCode:: " + response.getStatus() + ", ResponseBody:: " + response.getData());

            return response;
        } catch (Exception e) {
            logger.error("[RefundMoMoProcess] ", e);
        }

        return null;
    }
}