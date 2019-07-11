package com.mservice.shared.sharedmodels;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment {

    private String momoEndpoint;
    private PartnerInfo partnerInfo;
    private String target;

    public Environment(String momoEndpoint, PartnerInfo partnerInfo, String target) {
        this.momoEndpoint = momoEndpoint;
        this.partnerInfo = partnerInfo;
        this.target = target;
    }

    public String getMomoEndpoint() {
        return momoEndpoint;
    }

    public void setMomoEndpoint(String momoEndpoint) {
        this.momoEndpoint = momoEndpoint;
    }

    public PartnerInfo getPartnerInfo() {
        return partnerInfo;
    }

    public void setPartnerInfo(PartnerInfo partnerInfo) {
        this.partnerInfo = partnerInfo;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    /*
     * Create and modify your environment.properties file appropriately
     *
     * @param target name of the environment (dev or prod)
     * @param process name of the process the code is calling
     * @return
     **/
    public static Environment selectEnv(String target, String process) {
        try (InputStream input = Environment.class.getClassLoader().getResourceAsStream("environment.properties")) {

            Properties prop = new Properties();
            prop.load(input);

            String subDir = "";
            switch (process) {
                case "pay-gate":
                    subDir = prop.getProperty("ALL_IN_ONE_PAYGATE");
                    break;
                case "app-in-app":
                    subDir = prop.getProperty("APP_IN_APP");
                    break;
                case "pos":
                    subDir = prop.getProperty("POS");
                    break;
                case "query-status":
                    subDir = prop.getProperty("PAY_TRANSACTION_QUERY_STATUS");
                    break;
                case "pay-refund":
                    subDir = prop.getProperty("PAY_REFUND");
                    break;
                case "pay-confirm":
                    subDir = prop.getProperty("PAY_CONFIRM");
                    break;
            }

            switch (target) {
                case "dev":
                    PartnerInfo devInfo = new PartnerInfo(prop.getProperty("DEV_PARTNER_CODE"), prop.getProperty("DEV_ACCESS_KEY"), prop.getProperty("DEV_SECRET_KEY"));
                    Environment dev = new Environment(prop.getProperty("DEV_MOMO_ENDPOINT") + subDir, devInfo, prop.getProperty("DEV"));
                    return dev;
                case "prod":
                    PartnerInfo prodInfo = new PartnerInfo(prop.getProperty("PROD_PARTNER_CODE"), prop.getProperty("PROD_ACCESS_KEY"), prop.getProperty("PROD_SECRET_KEY"));
                    Environment production = new Environment(prop.getProperty("PROD_MOMO_ENDPOINT") + subDir, prodInfo, prop.getProperty("PROD"));
                    return production;
                default:
                    throw new IllegalArgumentException("MoMo doesnt provide other environment: dev and prod");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
