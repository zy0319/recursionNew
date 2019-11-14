package com.zy.recursion.Controller;

import com.zy.recursion.config.annotation;
import com.zy.recursion.config.linuxConfig;
import com.zy.recursion.entity.address;
import com.zy.recursion.entity.device;
import com.zy.recursion.entity.handleCache;
import com.zy.recursion.entity.returnMessage;
import com.zy.recursion.service.device.deviceService;
import com.zy.recursion.service.handleCache.handleCacheService;
import com.zy.recursion.util.ConnectLinuxCommand;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.io.*;
import java.net.URISyntaxException;
import java.util.List;
import com.zy.recursion.util.httpPost;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping(value = "/http", method = RequestMethod.GET)
@ResponseBody



public class http {

    @Autowired
    private address address;

    @Autowired
    private deviceService deviceService;


//    @Autowired
//    private handleCacheDao handleCacheDao;

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/sendPostDataByJson",produces = {"text/html;charset=UTF-8"})
    public String sendPostDataByJson(HttpServletResponse response, @RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requestBody);
        int receive = 0;
        int drop = 0;
        int reply = 0;
        int avg_rep = 0;
        int success = 0;
        int other = 0;
        float success_rate = 0;
        int hit_all = 0;
        float hit_rate = 0;
        int recur = 0;
        int avg_recur = 0;
        float recur_success = 0;
        float all_receive = 0;
        int handle  = 0;
        int dns  = 0;
        int oid  = 0;
        int ecode  = 0;
        int gs1  = 0;
        String total_time = null;
        String time = null;
        int cacheDeviceCount = 0;
        for (handleCache handleCache:linuxConfig.handleCaches){
            if (handleCache.getHandleCache().getString("nodeName").equals(jsonObject.getString("nodeName"))){
                JSONObject jsonObject1 = handleCache.getHandleCache();
                if (jsonObject1.getString("deviceType").equals("缓存")){
                    cacheDeviceCount++;
                    time = jsonObject1.getString("TIME");
                    receive = receive+jsonObject1.getInt("RECEIVE");
                    drop = drop+jsonObject1.getInt("DROP");
                    reply = reply+jsonObject1.getInt("REPLY");
                    avg_rep = avg_rep+jsonObject1.getInt("AVG_REP_TIME");
                    success = success+jsonObject1.getInt("SUCCESS");
                    other = other+jsonObject1.getInt("OTHER");
                    recur = recur+jsonObject1.getInt("RECUR");
                    avg_recur = avg_recur+jsonObject1.getInt("AVG_RECUR_TIME");
                    success_rate = success_rate+jsonObject1.getFloat("SUCCESS_RATE");
                    hit_all = hit_all + jsonObject1.getInt("HIT_ALL");
                    hit_rate = hit_rate+jsonObject1.getFloat("HIT_RATE");
                    recur_success = recur_success+jsonObject1.getFloat("RECUR_SUCCESS");
                    all_receive = all_receive+jsonObject1.getFloat("ALL_RECEIVE");
                    total_time = jsonObject1.getString("total_time");
                    handle = handle+jsonObject1.getInt("HANDLE");
                    dns = dns+jsonObject1.getInt("DNS");
                    oid = oid+jsonObject1.getInt("OID");
                    ecode = ecode+jsonObject1.getInt("ECODE");
                    gs1 = gs1+jsonObject1.getInt("GS1");
                }
            }
        }
        JSONObject jsonObject2 = new JSONObject();
        if (cacheDeviceCount != 0) {
            jsonObject2.put("AVG_REP_TIME", avg_rep / cacheDeviceCount);
            jsonObject2.put("SUCCESS_RATE", success_rate / cacheDeviceCount);
            jsonObject2.put("AVG_RECUR_TIME", avg_recur / cacheDeviceCount);
            jsonObject2.put("RECUR_SUCCESS", recur_success / cacheDeviceCount);
            jsonObject2.put("HIT_RATE", hit_rate / cacheDeviceCount);
        } else {
            jsonObject2.put("AVG_REP_TIME", avg_rep);
            jsonObject2.put("SUCCESS_RATE", success_rate);
            jsonObject2.put("AVG_RECUR_TIME", avg_recur);
            jsonObject2.put("RECUR_SUCCESS", recur_success);
            jsonObject2.put("HIT_RATE", hit_rate);
        }
        jsonObject2.put("TIME", time);
        jsonObject2.put("RECEIVE", receive);
        jsonObject2.put("DROP", drop);
        jsonObject2.put("REPLY", reply);
        jsonObject2.put("SUCCESS", success);
        jsonObject2.put("OTHER", other);
        jsonObject2.put("RECUR", recur);
        jsonObject2.put("HIT_ALL",hit_all);
        jsonObject2.put("ALL_RECEIVE", all_receive);
        jsonObject2.put("total_time", total_time);
        jsonObject2.put("HANDLE", handle);
        jsonObject2.put("DNS", dns);
        jsonObject2.put("OID", oid);
        jsonObject2.put("ECODE", ecode);
        jsonObject2.put("GS1", gs1);
        jsonObject2.put("nodeName", jsonObject.getString("nodeName"));
        return jsonObject2.toString();
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/sendPostDataByJson1",produces = {"text/html;charset=UTF-8"})
    public String sendPostDataByJson1(HttpServletResponse response, @RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requestBody);
        for (handleCache handleCache:linuxConfig.handleCaches){
            if (handleCache.getHandleCache().getString("deviceIp").equals(jsonObject.getString("ip"))){
                String logCache = handleCache.getHandleCache().toString();
                return logCache;
            }
        }
        return null;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/handleResolve")
    public returnMessage handleResolve(HttpServletResponse response, @RequestBody(required = false) String requestBody) throws IOException, URISyntaxException {
        JSONObject jsonObject = new JSONObject(requestBody);
        String prefixType = jsonObject.getString("prefixType");
        String ip =jsonObject.getString("ip");
        String prefix = jsonObject.getString("prefix");
        if (prefixType.equals("Handle")){
            if (ip.equals("39.107.238.25") || ip.equals("172.171.1.80") || ip.equals("172.171.1.79")){
                return httpPost.testSendGetDataByJson(jsonObject);
            }else{
                return null;
            }
        }else if (prefixType.equals("DNS")){
            if (ip.equals("172.171.1.80")){
                device device = deviceService.selectByIp1(ip);
                return new ConnectLinuxCommand().dns(device,ip,prefix);
            }else{
                return null;
            }
        }else if (prefixType.equals("Oid") || prefixType.equals("GS1") || prefixType.equals("Ecode")){
            if (ip.equals("172.171.1.80")){
                device device = deviceService.selectByIp1(ip);
                return new ConnectLinuxCommand().oid(device,ip,prefix);
            }else{
                return null;
            }
        }
        return null;
    }
}
