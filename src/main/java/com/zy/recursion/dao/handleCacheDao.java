package com.zy.recursion.dao;

import com.zy.recursion.entity.handleCacheLog;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface handleCacheDao {
    void addHandleCache(handleCacheLog handleCacheLog);
    List<handleCacheLog> selectHandleCache(String startTime, String endTime,String deviceIp);

}
