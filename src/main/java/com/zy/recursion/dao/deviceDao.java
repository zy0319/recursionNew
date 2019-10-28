package com.zy.recursion.dao;

import com.zy.recursion.entity.device;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface deviceDao {
    void addDevice(device device);
    List<device> selectAll();
    List<device> selectAll1();
    void deleteDevice(String deviceIp);
    void updateDevice(device device);
    void deleteDeviceByNodename(String nodeName);
    device selectByIp(String deviceIp);
    List<device> selectIpByNodeName(String nodeName);
    List<device> selectDeviceByNodeName(String nodeName);

}
