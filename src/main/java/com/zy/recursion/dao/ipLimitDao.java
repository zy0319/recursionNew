package com.zy.recursion.dao;

import com.zy.recursion.entity.ipLimit;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ipLimitDao {
    void addIpLimit(ipLimit ipLimit);
    List<ipLimit> selectIpLimit(String nodeIp);
    List<ipLimit> selectIpLimitByStatus(String status,String nodeIp);
    void deleteIpLimit(int id);
    void deleteIpLimitBySatus(int status,String nodeIp);
    void updateIpLimit(ipLimit ipLimit);
}
