package com.zy.recursion.service.device.impl;

import com.zy.recursion.dao.deviceDao;
import com.zy.recursion.entity.device;
import com.zy.recursion.service.device.deviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class deviceServiceImpl implements deviceService {

    @Autowired
    private deviceDao deviceDao;

    @Override
    public void addDevice(device device){
        deviceDao.addDevice(device);
    }

    @Override
    public List<device> selectAll(){
        return deviceDao.selectAll();
    }

    @Override
    public List<device> selectAll1(){
        return deviceDao.selectAll1();
    }

    @Override
    public void deleteDevice(String deviceIp){
        deviceDao.deleteDevice(deviceIp);
    }

    @Override
    public void updateDevice(device device){
        deviceDao.updateDevice(device);
    }

    @Override
    public void deleteDeviceByNodename(String nodeName){
        deviceDao.deleteDeviceByNodename(nodeName);
    }

    @Override
    public boolean selectByIp(String deviceIp){
        return deviceDao.selectByIp(deviceIp) == null;
    }

    @Override
    public device selectDeviceByIp(String deviceIp){
        return deviceDao.selectByIp(deviceIp);
    }

    @Override
    public List<device> selectIpByNodeName(String nodeName){
        return deviceDao.selectIpByNodeName(nodeName);
    }

    @Override
    public device selectByIp1(String deviceIp){
        return deviceDao.selectByIp(deviceIp);
    }

    @Override
    public List<device> selectDeviceByNodeName(String nodeName){
        return deviceDao.selectDeviceByNodeName(nodeName);
    }

}
