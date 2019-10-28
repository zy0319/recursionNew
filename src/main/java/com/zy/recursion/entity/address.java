package com.zy.recursion.entity;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "address")

public class address {

    private String address;
    private String networkCard;
    private int bandwidthSet;

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
}
