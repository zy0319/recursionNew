package com.zy.recursion.service.ipLimit.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy.recursion.dao.ipLimitDao;
import com.zy.recursion.entity.ipLimit;
import com.zy.recursion.entity.returnMessage;
import com.zy.recursion.service.ipLimit.ipLimitService;
import com.zy.recursion.util.ConnectLinuxCommand;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class ipLimitServiceImpl implements ipLimitService {

    @Autowired
    private ipLimitDao ipLimitDao;

    @Override
    public returnMessage addIpLimit(String ipAddress,int limitVal,String nodeIp){
        returnMessage returnMessage = new returnMessage();
        ipLimit ipLimit = new ipLimit();
        ipLimit.setDeviceIp(ipAddress);
        ipLimit.setLimit(limitVal);
        ipLimit.setStatus(1);
        ipLimit.setNodeIp(nodeIp);
        ipLimitDao.addIpLimit(ipLimit);
        returnMessage.setMessage("success");
        returnMessage.setStatus(1);
        return returnMessage;
    }

    @Override
    public String selectIpLimit(int pageNum,int pageSize,String filePath,String nodeIp) throws IOException {
        ipLimitDao.deleteIpLimitBySatus(0,nodeIp);
        List<ipLimit> limits = ConnectLinuxCommand.readFile(filePath,nodeIp);
        for (ipLimit limit : limits) {
            ipLimitDao.addIpLimit(limit);
        }
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<ipLimit> pageInfo = new PageInfo<>(ipLimitDao.selectIpLimit(nodeIp));
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("data",pageInfo.getList());
        jsonObject1.put("totalCount",pageInfo.getPages());
        return jsonObject1.toString();
    }

    @Override
    public returnMessage deleteIpLimit(int id,String filePath,String ipLimit,String deviceIp) throws IOException {
        ipLimitDao.deleteIpLimit(id);
        ConnectLinuxCommand.deleteFile(filePath,ipLimit,deviceIp);
        returnMessage returnMessage = new returnMessage();
        returnMessage.setMessage("success");
        returnMessage.setStatus(1);
        return returnMessage;
    }


    @Override
    public returnMessage sendIpLimitConfig(String filePath,String deviceIp) throws IOException {
        List<ipLimit> list = ipLimitDao.selectIpLimit(deviceIp);
        List<String> list1 = new ArrayList<>();
        for (ipLimit ipLimit : list) {
            list1.add(ipLimit.getDeviceIp()+"|"+ipLimit.getLimit());
        }
        List<ipLimit> list2 = ipLimitDao.selectIpLimitByStatus1("1","2",deviceIp);
        ConnectLinuxCommand.clearStringFromFile(filePath,deviceIp);
        ConnectLinuxCommand.writeStringToFile(filePath,list1,deviceIp);
        Stack result = ConnectLinuxCommand.sendSet(filePath,deviceIp);
        returnMessage returnMessage = new returnMessage();
        if (result.size()==1){
            for (ipLimit ipLimit : list2) {
                ipLimit.setStatus(0);
                ipLimitDao.updateIpLimit(ipLimit);
            }
            returnMessage.setMessage(result.pop().toString());
            returnMessage.setStatus(1);
        }
        else {
            for (ipLimit ipLimit : list2) {
                ipLimit.setStatus(2);
                ipLimitDao.updateIpLimit(ipLimit);
            }
            List<String> list3 = new ArrayList<>();
            List<ipLimit> list4 = ipLimitDao.selectIpLimitByStatus("0",deviceIp);
            for (ipLimit ipLimit : list4) {
                list3.add(ipLimit.getDeviceIp()+"|"+ipLimit.getLimit());
            }
            ConnectLinuxCommand.clearStringFromFile(filePath,deviceIp);
            ConnectLinuxCommand.writeStringToFile(filePath,list3,deviceIp);
            returnMessage.setMessage(result.pop().toString());
            returnMessage.setStatus(0);
        }
        return returnMessage;
    }



}
