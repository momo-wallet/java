package com.mservice.shared.sharedmodels;

import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import okhttp3.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Execute {

    public static final MediaType JSON = MediaType.get(Parameter.JSON_HEADER);
    public final Logger logger = LogManager.getLogger(getClass());

    OkHttpClient client = new OkHttpClient();

    public HttpResponse sendToMoMo(String endpoint, String payload) throws MoMoException {

        try {

            RequestBody requestBody = RequestBody.create(JSON, payload); //payload

            Request request = new Request.Builder()
                    .url(endpoint)
                    .post(requestBody)
                    .build();

            logger.debug("[HttpPostToMoMo] Endpoint:: " + request.url() + ", RequestBody:: " + request.body());

            Response result = client.newCall(request).execute();
            HttpResponse response = new HttpResponse(result.code(), result.body().string());

            logger.info("[HttpResponseFromMoMo] HttpStatusCode:: " + response.getStatus() + ", ResponseBody:: " + response.getData());

            return response;
        } catch (Exception e) {
            logger.error("[RefundMoMoProcess] ", e);
        }

        return null;
    }

}