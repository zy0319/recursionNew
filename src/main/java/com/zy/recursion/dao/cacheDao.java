package com.zy.recursion.dao;

import com.zy.recursion.entity.cache;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface cacheDao {
    void addCache(cache cache);
    List<cache> selectCache(String startTime,String endTime);
}
