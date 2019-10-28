package com.zy.recursion.entity;

import java.io.Serializable;
public class linuxMessage implements Serializable {

    private  Float diskUtilization;
    private  Float memoryUtilization;
    private  Float cpuUtilization;
    private  Float rxkB;
    private  Float txkB;
    private  String handle_cache_stats;
    private  String deviceIp;

    public linuxMessage(){
        diskUtilization = 0f;
        memoryUtilization = 0f;
        cpuUtilization = 0f;
        rxkB = 0f;
        txkB = 0f;
        handle_cache_stats = "";
    }

    public Float getDiskUtilization() {
        return diskUtilization;
    }

    public void setDiskUtilization(Float diskUtilization) {
        this.diskUtilization = diskUtilization;
    }

    public Float getMemoryUtilization() {
        return memoryUtilization;
    }

    public void setMemoryUtilization(Float memoryUtilization) {
        this.memoryUtilization = memoryUtilization;
    }

    public Float getCpuUtilization() {
        return cpuUtilization;
    }

    public void setCpuUtilization(Float cpuUtilization) {
        this.cpuUtilization = cpuUtilization;
    }

    public Float getRxkB() {
        return rxkB;
    }

    public void setRxkB(Float rxkB) {
        this.rxkB = rxkB;
    }

    public Float getTxkB() {
        return txkB;
    }

    public void setTxkB(Float txkB) {
        this.txkB = txkB;
    }

    public String getHandle_cache_stats() {
        return handle_cache_stats;
    }

    public void setHandle_cache_stats(String handle_cache_stats) {
        this.handle_cache_stats = handle_cache_stats;
    }

    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    @Override
    public String toString() {
        return "linuxMessage{" +
                "diskUtilization=" + diskUtilization +
                ", memoryUtilization=" + memoryUtilization +
                ", cpuUtilization=" + cpuUtilization +
                ", rxkB=" + rxkB +
                ", txkB=" + txkB +
                ", handle_cache_stats='" + handle_cache_stats + '\'' +
                ", deviceIp='" + deviceIp + '\'' +
                '}';
    }
}
