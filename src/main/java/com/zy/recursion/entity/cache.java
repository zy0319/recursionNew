package com.zy.recursion.entity;

import java.sql.Timestamp;

public class cache {

    private Timestamp deleteTime;
    private String deleteOperation;
    private String deleteResult;
    private String handle;
    private String nodeName;
    private String deviceIp;

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public Timestamp getDeleteTime() {
        return deleteTime;
    }

    public void setDeleteTime(Timestamp deleteTime) {
        this.deleteTime = deleteTime;
    }

    public String getDeleteOperation() {
        return deleteOperation;
    }

    public void setDeleteOperation(String deleteOperation) {
        this.deleteOperation = deleteOperation;
    }

    public String getDeleteResult() {
        return deleteResult;
    }

    public void setDeleteResult(String deleteResult) {
        this.deleteResult = deleteResult;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }
}
