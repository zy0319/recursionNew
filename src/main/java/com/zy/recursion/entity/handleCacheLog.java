package com.zy.recursion.entity;

import java.io.Serializable;
import java.sql.Timestamp;

public class handleCacheLog implements Serializable {

    private Timestamp currentTime;
    private String TIME;
    private int RECEIVE;
    private int DROP;
    private int REPLY;
    private int AVG_REP_TIME;
    private int SUCCESS;
    private int OTHER;
    private float SUCCESS_RATE;
    private float HIT_RATE;
    private int RECUR;
    private int AVG_RECUR_TIME;
    private float RECUR_SUCCESS;
    private float ALL_RECEIVE;
    private String total_time;
    private int HANDLE;
    private int DNS;
    private int OID;
    private int ECODE;
    private int GS1;
    private String deviceIp;

    public Timestamp getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(Timestamp currentTime) {
        this.currentTime = currentTime;
    }

    public String getTIME() {
        return TIME;
    }

    public void setTIME(String TIME) {
        this.TIME = TIME;
    }

    public int getRECEIVE() {
        return RECEIVE;
    }

    public void setRECEIVE(int RECEIVE) {
        this.RECEIVE = RECEIVE;
    }

    public int getDROP() {
        return DROP;
    }

    public void setDROP(int DROP) {
        this.DROP = DROP;
    }

    public int getREPLY() {
        return REPLY;
    }

    public void setREPLY(int REPLY) {
        this.REPLY = REPLY;
    }

    public int getAVG_REP_TIME() {
        return AVG_REP_TIME;
    }

    public void setAVG_REP_TIME(int AVG_REP_TIME) {
        this.AVG_REP_TIME = AVG_REP_TIME;
    }

    public int getSUCCESS() {
        return SUCCESS;
    }

    public void setSUCCESS(int SUCCESS) {
        this.SUCCESS = SUCCESS;
    }

    public int getOTHER() {
        return OTHER;
    }

    public void setOTHER(int OTHER) {
        this.OTHER = OTHER;
    }

    public float getSUCCESS_RATE() {
        return SUCCESS_RATE;
    }

    public void setSUCCESS_RATE(float SUCCESS_RATE) {
        this.SUCCESS_RATE = SUCCESS_RATE;
    }

    public float getHIT_RATE() {
        return HIT_RATE;
    }

    public void setHIT_RATE(float HIT_RATE) {
        this.HIT_RATE = HIT_RATE;
    }

    public int getRECUR() {
        return RECUR;
    }

    public void setRECUR(int RECUR) {
        this.RECUR = RECUR;
    }

    public int getAVG_RECUR_TIME() {
        return AVG_RECUR_TIME;
    }

    public void setAVG_RECUR_TIME(int AVG_RECUR_TIME) {
        this.AVG_RECUR_TIME = AVG_RECUR_TIME;
    }

    public float getRECUR_SUCCESS() {
        return RECUR_SUCCESS;
    }

    public void setRECUR_SUCCESS(float RECUR_SUCCESS) {
        this.RECUR_SUCCESS = RECUR_SUCCESS;
    }

    public float getALL_RECEIVE() {
        return ALL_RECEIVE;
    }

    public void setALL_RECEIVE(float ALL_RECEIVE) {
        this.ALL_RECEIVE = ALL_RECEIVE;
    }

    public String getTotal_time() {
        return total_time;
    }

    public void setTotal_time(String total_time) {
        this.total_time = total_time;
    }

    public int getHANDLE() {
        return HANDLE;
    }

    public void setHANDLE(int HANDLE) {
        this.HANDLE = HANDLE;
    }

    public int getDNS() {
        return DNS;
    }

    public void setDNS(int DNS) {
        this.DNS = DNS;
    }

    public int getOID() {
        return OID;
    }

    public void setOID(int OID) {
        this.OID = OID;
    }

    public int getECODE() {
        return ECODE;
    }

    public void setECODE(int ECODE) {
        this.ECODE = ECODE;
    }

    public int getGS1() {
        return GS1;
    }

    public void setGS1(int GS1) {
        this.GS1 = GS1;
    }



    public String getDeviceIp() {
        return deviceIp;
    }

    public void setDeviceIp(String deviceIp) {
        this.deviceIp = deviceIp;
    }

    @Override
    public String toString() {
        return "handleCacheLog{" +
                "TIME='" + TIME + '\'' +
                ", RECEIVE=" + RECEIVE +
                ", DROP=" + DROP +
                ", REPLY=" + REPLY +
                ", AVG_REP_TIME=" + AVG_REP_TIME +
                ", SUCCESS=" + SUCCESS +
                ", OTHER=" + OTHER +
                ", SUCCESS_RATE=" + SUCCESS_RATE +
                ", HIT_RATE=" + HIT_RATE +
                ", RECUR=" + RECUR +
                ", AVG_RECUR_TIME=" + AVG_RECUR_TIME +
                ", RECUR_SUCCESS=" + RECUR_SUCCESS +
                ", ALL_RECEIVE=" + ALL_RECEIVE +
                ", total_time='" + total_time + '\'' +
                ", HANDLE=" + HANDLE +
                ", DNS=" + DNS +
                ", OID=" + OID +
                ", ECODE=" + ECODE +
                ", GS1=" + GS1 +
                ", deviceIp='" + deviceIp + '\'' +
                '}';
    }
}
