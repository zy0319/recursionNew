package com.zy.recursion.Controller;


import com.zy.recursion.config.annotation;
import com.zy.recursion.entity.address;
import com.zy.recursion.entity.returnMessage;
import com.zy.recursion.service.ipLimit.ipLimitService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequestMapping(value = "/ipLimit", method = RequestMethod.GET)
public class ipLimitController {

    @Autowired
    private ipLimitService ipLimitService;


    @Autowired
    private address address;

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/addIp")
    public returnMessage addIP(@RequestBody(required = false) String requesyBody){
        JSONObject jsonObject = new JSONObject(requesyBody);
        String ipAddress = jsonObject.getString("ipAddress");
        String limitVal = jsonObject.getString("limitVal");
        String nodeIp = jsonObject.getString("nodeIp");
        return ipLimitService.addIpLimit(ipAddress, Integer.parseInt(limitVal),nodeIp);
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/ipList")
    public String ipList(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        String nodeIp = jsonObject.getString("nodeIp");
        int pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
        int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));
        return ipLimitService.selectIpLimit(pageNum,pageSize,address.getIpLimitPath(),nodeIp);
    }


    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/delIP")
    public returnMessage delIP(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        int id = Integer.parseInt(jsonObject.getString("id"));
        String nodeIp = jsonObject.getString("nodeIp");
        String limitVal = jsonObject.getString("limitVal");
        return ipLimitService.deleteIpLimit(id,address.getIpLimitPath(),limitVal,nodeIp);
    }


    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/sendIpLimitConfig")
    public returnMessage sendIpLimitConfig(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        String deviceIp = jsonObject.getString("nodeIp");
        return ipLimitService. sendIpLimitConfig(address.getIpLimitPath(),deviceIp);
    }


}
