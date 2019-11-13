package com.zy.recursion.service.handleCache.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy.recursion.dao.handleCacheDao;
import com.zy.recursion.entity.handleCache;
import com.zy.recursion.entity.handleCacheLog;
import com.zy.recursion.service.handleCache.handleCacheService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class handleCacheServiceImpl implements handleCacheService {

    @Autowired
    handleCacheDao handleCacheDao;

    @Override
    public void addHandleCache(handleCache handleCache){
        if (handleCache == null){
            return;
        }
        JSONObject jsonObject1 = handleCache.getHandleCache();
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String addTime = dateFormat.format(date);
        Timestamp ts = Timestamp.valueOf(addTime);
        handleCacheLog handleCacheLog = new handleCacheLog();
        handleCacheLog.setTIME(jsonObject1.getString("TIME"));
        handleCacheLog.setRECEIVE(jsonObject1.getInt("RECEIVE"));
        handleCacheLog.setDROP(jsonObject1.getInt("DROP"));
        handleCacheLog.setREPLY(jsonObject1.getInt("REPLY"));
        handleCacheLog.setAVG_REP_TIME(jsonObject1.getInt("AVG_REP_TIME"));
        handleCacheLog.setSUCCESS(jsonObject1.getInt("SUCCESS"));
        handleCacheLog.setOTHER(jsonObject1.getInt("OTHER"));
        handleCacheLog.setRECUR(jsonObject1.getInt("RECUR"));
        handleCacheLog.setAVG_RECUR_TIME(jsonObject1.getInt("AVG_RECUR_TIME"));
        handleCacheLog.setSUCCESS_RATE(jsonObject1.getFloat("SUCCESS_RATE"));
        handleCacheLog.setHIT_RATE(jsonObject1.getFloat("HIT_RATE"));
        handleCacheLog.setHIT_ALL(jsonObject1.getInt("HIT_ALL"));
        handleCacheLog.setRECUR_SUCCESS(jsonObject1.getFloat("RECUR_SUCCESS"));
        handleCacheLog.setALL_RECEIVE(jsonObject1.getFloat("ALL_RECEIVE"));
        handleCacheLog.setTotal_time(jsonObject1.getString("total_time"));
        handleCacheLog.setHANDLE(jsonObject1.getInt("HANDLE"));
        handleCacheLog.setOID(jsonObject1.getInt("OID"));
        handleCacheLog.setDNS(jsonObject1.getInt("DNS"));
        handleCacheLog.setECODE(jsonObject1.getInt("ECODE"));
        handleCacheLog.setGS1(jsonObject1.getInt("GS1"));
        handleCacheLog.setDeviceIp(jsonObject1.getString("deviceIp"));
        handleCacheLog.setCurrentTime(ts);
        System.out.println(handleCacheLog.toString());
        handleCacheDao.addHandleCache(handleCacheLog);
    }


    @Override
    public String selectHandleCache(String startTime, String endTime,int page,int pageSize,String deviceIp){
//        List list = cacheService.selectCache(startTime,endTime);
        PageHelper.startPage(page,pageSize);
        // 设置分页查询条件
//        Example example = new Example(cache.class);
        PageInfo<handleCacheLog> pageInfo = new PageInfo<>(handleCacheDao.selectHandleCache(startTime,endTime,deviceIp));
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("sum",pageInfo.getTotal());
        jsonObject1.put("data",pageInfo.getList());
        jsonObject1.put("pages",pageInfo.getPages());
        return jsonObject1.toString();
    }
}
