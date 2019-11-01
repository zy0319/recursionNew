package com.zy.recursion.Controller;


import com.zy.recursion.config.annotation;
import com.zy.recursion.service.handleCache.handleCacheService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/handleCache", method = RequestMethod.GET)
public class handleCacheLog {


    @Autowired
    handleCacheService handleCacheService;

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/handleCacheSelect",produces = {"text/html;charset=UTF-8"})
    public String cacheSelect(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        return handleCacheService.selectHandleCache(jsonObject.getString("startTime"),jsonObject.getString("endTime"),jsonObject.getInt("page"),jsonObject.getInt("pageSize"),jsonObject.getString("deviceIp"));
    }


}
