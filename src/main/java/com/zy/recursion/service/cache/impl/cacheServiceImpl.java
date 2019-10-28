package com.zy.recursion.service.cache.impl;

import com.zy.recursion.dao.cacheDao;
import com.zy.recursion.entity.cache;
import com.zy.recursion.service.cache.cacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class cacheServiceImpl implements cacheService {

    @Autowired
    cacheDao cacheDao;

    @Override
    public void addCache(cache cache){
        cacheDao.addCache(cache);
    }

    @Override
    public List<cache> selectCache(String startTime,String endTime){
        return cacheDao.selectCache(startTime,endTime);
    }
}
