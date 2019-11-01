package com.zy.recursion.service.sysRecord;


import com.zy.recursion.entity.returnMessage;

import java.io.IOException;

public interface sysRecordService {
    returnMessage addSysRecord(String handleName,int ttl, int index, String type,String data,String nodeIp);
    String selectSysRecord(int pageNum,int pageSize,String filePath,String nodeIp) throws IOException;
    returnMessage deleteSysRecord(int id,String filePath,String sysRecord,String nodeIp) throws IOException;
    returnMessage sendSysRecordConfig(String filePath,String nodeIp) throws IOException;
}
