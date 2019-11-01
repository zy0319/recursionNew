package com.zy.recursion.entity;

import java.io.Serializable;

public class ipLimit implements Serializable {
    private String deviceIp;
    private int limit;
    private int status;
    private int id;
    private String nodeIp;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    @Override
    public String toString() {
        return "ipLimit{" +
                "deviceIp='" + deviceIp + '\'' +
                ", limit=" + limit +
                ", status=" + status +
                ", id=" + id +
                ", nodeIp='" + nodeIp + '\'' +
                '}';
    }
}
