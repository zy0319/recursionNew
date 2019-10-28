package com.zy.recursion.service.cache;

import com.zy.recursion.entity.cache;

import java.util.List;

public interface cacheService {
    void addCache(cache cache);
    List<cache> selectCache(String startTime,String endTime);



}
