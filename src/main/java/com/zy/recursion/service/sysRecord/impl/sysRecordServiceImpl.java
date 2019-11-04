package com.zy.recursion.service.sysRecord.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy.recursion.dao.sysRecordDao;
import com.zy.recursion.entity.returnMessage;
import com.zy.recursion.entity.sysRecord;
import com.zy.recursion.service.sysRecord.sysRecordService;
import com.zy.recursion.util.ConnectLinuxCommand;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@Service
public class sysRecordServiceImpl implements sysRecordService {

    @Autowired
    private sysRecordDao sysRecordDao;

    @Override
    public returnMessage addSysRecord(String handleName, int ttl, int index, String type, String data,String nodeIp){
        returnMessage returnMessage = new returnMessage();
        sysRecord sysRecord = new sysRecord();
        sysRecord.setData(data);
        sysRecord.setHandleName(handleName);
        sysRecord.setType(type);
        sysRecord.setIndex(index);
        sysRecord.setTtl(ttl);
        sysRecord.setStatus(1);
        sysRecord.setNodeIp(nodeIp);
        sysRecordDao.addSysRecord(sysRecord);
        returnMessage.setMessage("success");
        returnMessage.setStatus(1);
        return returnMessage;
    }

    @Override
    public String selectSysRecord(int pageNum,int pageSize,String filePath,String nodeIp) throws IOException{
        sysRecordDao.deleteSysRecordBySatus(0,nodeIp);
        List<sysRecord> sysRecords = ConnectLinuxCommand.readFile1(filePath,nodeIp);
        for (sysRecord sysRecord : sysRecords) {
            sysRecordDao.addSysRecord(sysRecord);
        }
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<sysRecord> pageInfo = new PageInfo<>(sysRecordDao.selectSysRecord(nodeIp));
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("data",pageInfo.getList());
        jsonObject1.put("totalCount",pageInfo.getPages());
        return jsonObject1.toString();
    }

    @Override
    public returnMessage deleteSysRecord(int id,String filePath,String sysRecord,String nodeIp) throws IOException{
        sysRecordDao.deleteSysRecord(id);
        ConnectLinuxCommand.deleteFile(filePath,sysRecord,nodeIp);
        returnMessage returnMessage = new returnMessage();
        returnMessage.setMessage("success");
        returnMessage.setStatus(1);
        return returnMessage;
    }

    @Override
    public returnMessage sendSysRecordConfig(String filePath,String nodeIp) throws IOException{
        List<sysRecord> list = sysRecordDao.selectSysRecord(nodeIp);
        List<String> list1 = new ArrayList<>();
        list1.add("#强解记录集ID | 强解handle名 | TTL | INDEX | TYPE | data");
        int n=1;
        for (sysRecord sysRecord : list) {
            list1.add(n+"|"+sysRecord.getHandleName()+"|"+sysRecord.getTtl()+"|"+sysRecord.getIndex()+"|"+sysRecord.getType()+"|"+sysRecord.getData());
            n++;
        }
        List<sysRecord> list2 = sysRecordDao.selectSysRecordByStatus("1",nodeIp);
        for (sysRecord sysRecord : list2) {
            sysRecord.setStatus(0);
            sysRecordDao.updateSysRecord(sysRecord);
        }
        ConnectLinuxCommand.clearStringFromFile(filePath,nodeIp);
        ConnectLinuxCommand.writeStringToFile1(filePath,list1,nodeIp);
        Stack result = ConnectLinuxCommand.sendSet1(filePath,nodeIp);
        returnMessage returnMessage = new returnMessage();
        if (result.size()==1){
            returnMessage.setMessage(result.pop().toString());
            returnMessage.setStatus(1);
        }
        else {
            returnMessage.setMessage(result.pop().toString());
            returnMessage.setStatus(0);
        }
        return returnMessage;
    }



}
