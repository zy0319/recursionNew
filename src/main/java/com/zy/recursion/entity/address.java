package com.zy.recursion.entity;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "address")

public class address {

    private String address;
    private String networkCard;
    private int bandwidthSet;
    private String javaRefresh;
    private String javaRefresh1;
    private String ipLimitPath;
    private String sysPath;

    public int getBandwidthSet() {
        return bandwidthSet;
    }

    public void setBandwidthSet(int bandwidthSet) {
        this.bandwidthSet = bandwidthSet;
    }

    public String getNetworkCard() {
        return networkCard;
    }

    public void setNetworkCard(String networkCardName) {
        this.networkCard = networkCardName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getJavaRefresh() {
        return javaRefresh;
    }

    public void setJavaRefresh(String javaRefresh) {
        this.javaRefresh = javaRefresh;
    }

    public String getJavaRefresh1() {
        return javaRefresh1;
    }

    public void setJavaRefresh1(String javaRefresh1) {
        this.javaRefresh1 = javaRefresh1;
    }

    public String getIpLimitPath() {
        return ipLimitPath;
    }

    public void setIpLimitPath(String ipLimitPath) {
        this.ipLimitPath = ipLimitPath;
    }

    public String getSysPath() {
        return sysPath;
    }

    public void setSysPath(String sysPath) {
        this.sysPath = sysPath;
    }
}
