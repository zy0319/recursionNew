package com.zy.recursion.service.ipLimit;
import com.zy.recursion.entity.returnMessage;
import java.io.IOException;


public interface ipLimitService {
    returnMessage addIpLimit(String ipAddress, int limitVal,String deviceIp);
    String selectIpLimit(int pageNum,int pageSize,String filePath,String deviceIp) throws IOException;
    returnMessage deleteIpLimit(int id,String filePath,String ipLimit,String deviceIp) throws IOException;
    returnMessage sendIpLimitConfig(String filePath,String deviceIp) throws IOException;

}
