package com.zy.recursion.service.device;

import com.zy.recursion.entity.device;

import java.util.List;

public interface deviceService {
    void addDevice(device device);
    List<device> selectAll();
    List<device> selectAll1();
    void deleteDevice(String deviceIp);
    void updateDevice(device device);
    void deleteDeviceByNodename(String nodeName);
    boolean selectByIp(String deviceIp);
    device selectByIp1(String deviceIp);
    device selectDeviceByIp(String deviceIp);
    List<device> selectIpByNodeName(String nodeName);
    List<device> selectDeviceByNodeName(String nodeName);
}
