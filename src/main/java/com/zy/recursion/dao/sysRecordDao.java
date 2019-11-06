package com.zy.recursion.dao;


import com.zy.recursion.entity.sysRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface sysRecordDao {
    void addSysRecord(sysRecord sysRecord);
    List<sysRecord> selectSysRecord(String nodeIp);
    List<sysRecord> selectSysRecordByStatus(String status,String nodeIp);
    List<sysRecord> selectSysRecordByStatus1(String status1,String status2,String nodeIp);
    void deleteSysRecord(int id);
    void deleteSysRecordBySatus(int status,String nodeIp);
    void updateSysRecord(sysRecord sysRecord);

}
