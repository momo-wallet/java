package com.mservice.shared.sharedmodels;

import com.mservice.shared.exception.MoMoException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Environment {

    private String momoEndpoint;
    private PartnerInfo partnerInfo;
    private String target;

    public Environment(String momoEndpoint, PartnerInfo partnerInfo, EnvTarget target) {
        this(momoEndpoint, partnerInfo, target.string());
    }

    public Environment(String momoEndpoint, PartnerInfo partnerInfo, String target) {
        this.momoEndpoint = momoEndpoint;
        this.partnerInfo = partnerInfo;
        this.target = target;
    }

    /**
     *
     * @param target String target name ("dev" or "prod")
     * @param process
     * @return
     * @throws IllegalArgumentException
     */
    public static Environment selectEnv(String target, ProcessType process) throws IllegalArgumentException {
        switch(target) {
            case "dev":
                return selectEnv(EnvTarget.DEV, process);
            case "prod":
                return selectEnv(EnvTarget.PROD, process);
            default:
                throw new IllegalArgumentException("MoMo doesnt provide other environment: dev and prod");
        }
    }

    /**
     * Select appropriate environment to run processes
     * Create and modify your environment.properties file appropriately
     *
     * @param target EnvTarget (choose DEV or PROD)
     * @param process ProcessType
     * @return
     */
    public static Environment selectEnv(EnvTarget target, ProcessType process) {
        try (InputStream input = Environment.class.getClassLoader().getResourceAsStream("environment.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            String subDir = process.getSubDir(prop);

            switch (target) {
                case DEV:
                    PartnerInfo devInfo = new PartnerInfo(prop.getProperty("DEV_PARTNER_CODE"), prop.getProperty("DEV_ACCESS_KEY"), prop.getProperty("DEV_SECRET_KEY"));
                    Environment dev = new Environment(prop.getProperty("DEV_MOMO_ENDPOINT") + subDir, devInfo, target);
                    return dev;
                case PROD:
                    PartnerInfo prodInfo = new PartnerInfo(prop.getProperty("PROD_PARTNER_CODE"), prop.getProperty("PROD_ACCESS_KEY"), prop.getProperty("PROD_SECRET_KEY"));
                    Environment prod = new Environment(prop.getProperty("PROD_MOMO_ENDPOINT") + subDir, prodInfo, target);
                    return prod;
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

    public enum EnvTarget {
        DEV("development"), PROD("production");

        private String target;

        EnvTarget(String target) {
            this.target = target;
        }

        public String string() {
            return this.target;
        }
    }

    public enum ProcessType {
        PAY_GATE, APP_IN_APP, PAY_POS, PAY_QUERY_STATUS, PAY_REFUND, PAY_CONFIRM;

        public String getSubDir(Properties prop) {
            return prop.getProperty(this.toString());
        }
    }


}