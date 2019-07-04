package com.mservice.shared.sharedmodels;

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

    /**select environmemt
     * @param target name of the environment (dev or prod)
     * @return
     **/
    public static Environment selectEnv(String target) {
        switch (target) {
            case "dev":
                PartnerInfo devInfo = new PartnerInfo("MOMOLRJZ20181206", "mTCKt9W3eU1m39TW", "KqBEecvaJf1nULnhPF5htpG3AMtDIOlD");
                Environment dev = new Environment("https://test-payment.momo.vn/", devInfo, "development");
                return dev;
            case "prod":
                PartnerInfo productionInfo = new PartnerInfo("MOMO", "F8BBA842ECF85", "K951B6PE1waDMi640xX08PD3vg6EkVlz");
                Environment production = new Environment("https://payment.momo.vn/", productionInfo, "production");
                return production;
            default:
                throw new IllegalArgumentException("MoMo doesnt provide other environment: dev and prod");
        }
    }

}
