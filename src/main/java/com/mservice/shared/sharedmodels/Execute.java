package com.mservice.shared.sharedmodels;

import com.mservice.shared.constants.Parameter;
import com.mservice.shared.exception.MoMoException;
import okhttp3.*;
import okio.Buffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Execute {

    public static final MediaType JSON = MediaType.get(Parameter.JSON_HEADER);
    public final Logger logger = LoggerFactory.getLogger(getClass());
    private final static Buffer buffer = new Buffer();

    OkHttpClient client = new OkHttpClient();

    private static String requestBodyToString(RequestBody requestBody) throws IOException {
        requestBody.writeTo(buffer);
        return buffer.readUtf8();
    }

    public HttpResponse sendToMoMo(String endpoint, String payload) throws MoMoException {

        try {

            RequestBody requestBody = RequestBody.create(JSON, payload); //payload

            Request request = new Request.Builder()
                    .url(endpoint)
                    .post(requestBody)
                    .build();

            logger.debug("[HttpPostToMoMo] Endpoint:: " + request.url() + ", RequestBody:: " + requestBodyToString(request.body()));

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