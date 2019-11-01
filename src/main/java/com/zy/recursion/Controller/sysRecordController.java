package com.zy.recursion.Controller;

import com.zy.recursion.config.annotation;
import com.zy.recursion.entity.returnMessage;
import com.zy.recursion.service.sysRecord.sysRecordService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;

@RestController
@RequestMapping(value = "/forceAnalysis", method = RequestMethod.GET)
public class sysRecordController {

    @Autowired
    private sysRecordService sysRecordService;


    @Autowired
    private com.zy.recursion.entity.address address;

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/addHandle")
    public returnMessage addHandle(@RequestBody(required = false) String requesyBody){
        JSONObject jsonObject = new JSONObject(requesyBody);
        String handleName = jsonObject.getString("handleName");
        int ttl = Integer.parseInt(jsonObject.getString("ttl"));
        int index = Integer.parseInt(jsonObject.getString("index"));
        String type = jsonObject.getString("type");
        String data = jsonObject.getString("data");
        String nodeIp = jsonObject.getString("nodeIp");
        return sysRecordService.addSysRecord(handleName,ttl,index,type,data,nodeIp);
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/handleList")
    public String handleList(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        String nodeIp = jsonObject.getString("nodeIp");
        int pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
        int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));
        return sysRecordService.selectSysRecord(pageNum,pageSize,address.getSysPath(),nodeIp);
    }


    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/delHandle")
    public returnMessage delHandle(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        int id = Integer.parseInt(jsonObject.getString("id"));
        String nodeIp = jsonObject.getString("nodeIp");
        String sysRecord = jsonObject.getString("sysRecord").replace("/","\\/");
        return sysRecordService.deleteSysRecord(id,address.getSysPath(),sysRecord,nodeIp);
    }


    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/sendHandleConfig")
    public returnMessage sendHandleConfig(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        String nodeIp = jsonObject.getString("nodeIp");
        return sysRecordService.sendSysRecordConfig(address.getSysPath(),nodeIp);
    }


}
