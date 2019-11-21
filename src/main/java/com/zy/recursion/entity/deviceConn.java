package com.zy.recursion.entity;

import ch.ethz.ssh2.Connection;

public class deviceConn {
    private int id;
    private String deviceIp;
    private Connection connection;

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

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public String toString(){
        return "deviceIP : "+deviceIp + "connection : "+connection;
    }

}
