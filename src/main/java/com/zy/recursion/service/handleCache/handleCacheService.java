package com.zy.recursion.service.handleCache;

import com.zy.recursion.entity.handleCache;
import com.zy.recursion.entity.handleCacheLog;

import java.util.List;

public interface handleCacheService {
    void addHandleCache(handleCache handleCache);
    String selectHandleCache(String startTime, String endTime,int page,int pageSize);
}
