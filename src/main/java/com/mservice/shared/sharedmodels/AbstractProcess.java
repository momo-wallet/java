package com.mservice.shared.sharedmodels;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mservice.shared.exception.MoMoException;
import com.mservice.shared.utils.Execute;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author hainguyen
 * Documention: https://developers.momo.vn
 */
public abstract class AbstractProcess<T, V> {

    protected final Logger logger;
    protected PartnerInfo partnerInfo;
    protected Environment environment;
    protected Execute execute = new Execute();

    public AbstractProcess(Environment environment) {
        this.environment = environment;
        this.partnerInfo = environment.getPartnerInfo();
        this.logger = LoggerFactory.getLogger(getClass());
    }

    public static Gson getGson() {
        return new GsonBuilder()
                .disableHtmlEscaping()
                .create();
    }

    /**
     * Some errors will be showed in process
     * Read detail error in documents
     * [Find out] (https:/developers.momo.vn) - Section 7
     *
     * @param errorCode
     * @throws Exception
     */
    public static void errorMoMoProcess(int errorCode) throws MoMoException {

        switch (errorCode) {
            case 0:
                // O is meaning success
                break;
            case 1:
                throw new MoMoException("Empty access key or partner code");
        }
    }

    public abstract V execute(T request) throws MoMoException;
}
