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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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
        Iterator<Map.Entry<String, handleCache>> entries = linuxConfig.handleCachesMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, handleCache> entry = entries.next();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            handleCache handleCache = entry.getValue();
            if (handleCache.getHandleCache() != null && handleCache.getHandleCache().getString("nodeName").equals(jsonObject.getString("nodeName"))){
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
        if (linuxConfig.handleCachesMap.containsKey(jsonObject.getString("ip"))){
            handleCache handleCache = linuxConfig.handleCachesMap.get(jsonObject.getString("ip"));
            String logCache = handleCache.getHandleCache().toString();
            return logCache;
        }

        return null;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/handleResolve",produces = "text/plain;charset=UTF-8")
    public String handleResolve(HttpServletResponse response, @RequestBody(required = false) String requestBody) throws IOException, URISyntaxException {
        JSONObject jsonObject = new JSONObject(requestBody);
        //String prefixType = jsonObject.getString("prefixType");
        String ip =jsonObject.getString("ip");
        String prefix = jsonObject.getString("prefix");
        boolean isSingular = false;
        if (jsonObject.has("flag"))
        {
             isSingular = true;
        }
        JSONObject json = new JSONObject();
        returnMessage returnMessage;
        String prefixType = " ";
        String  handlepattern1="20.500.";
        //String  Gsqpattern="^[+]{0,1}(\\d+)$";
        String  Gsqpattern= "^\\d{5,}$";
        String  Dnspattern="[a-zA-Z0-9][-a-zA-Z0-9]{0,62}(\\.[a-zA-Z0-9][-a-zA-Z0-9]{0,62})+\\.?";
        String niotpantter = "cn.pub.xty.100";
        String ecodepantter = "100036930100";
        String oidpanntter = "1.2.156.86";


        if (Pattern.matches(Dnspattern, prefix))
        {
            prefixType="DNS";
        }
        if (prefix.startsWith(handlepattern1))
        {
            prefixType="Handle";
        }
        if(Pattern.matches(Gsqpattern, prefix))
        {
            prefixType="GS1";
        }
        if(prefix.startsWith(ecodepantter) )
        {
            prefixType="Ecode";
        }

        if(prefix.startsWith(oidpanntter) )
        {
            prefixType="Oid";
        }


        String  handlepattern[] = new String[]{"10", "11", "20", "21", "22", "25", "27", "77", "44", "86","0.NA"};
       if  (prefixType.equals("DNS")||prefixType.equals(" "))
        {
        for (String str : handlepattern) {
            if (prefix.startsWith(str)) {
                if (ip != null) {
                    jsonObject.put("prefixType","Handle");
                    returnMessage=httpPost.testSendGetDataByJson(jsonObject);
                    if (returnMessage.getStatus()==1)
                    {
                        break;
                    }else if(returnMessage.getMessage().startsWith("{\"handle")){
                        break;

                    }
                    else {
                        json.put("status",returnMessage.getStatus());
                        json.put("message",returnMessage.getMessage());
                        json.put("type","Handle");
                        return json.toString();
                    }
                }
            }
        }
       }
        if(prefixType.equals(" "))
        {
            json.put("status",1);
            json.put("message","未能查询到相应结果");
            json.put("type","未知");
            return json.toString();
        }

        jsonObject.put("prefixType",prefixType);

        if (prefixType.equals("Handle")){
            if (ip != null){
                returnMessage=httpPost.testSendGetDataByJson(jsonObject);
                if(returnMessage.getMessage().endsWith(":100}")||returnMessage.getMessage().endsWith("responseCode\":3}"))
                {
                    json.put("status", 1);
                    json.put("message", "未查询到此条handle");
                    json.put("type", prefixType);
                    return json.toString();
                }else {
                    json.put("status", returnMessage.getStatus());
                    json.put("message", returnMessage.getMessage());
                    json.put("type", prefixType);
                    return json.toString();
                }
                //return httpPost.testSendGetDataByJson(jsonObject);
            }else{
                return null;
            }
        }else if (prefixType.equals("DNS")){
            if (ip != null){
                device device = deviceService.selectByIp1(ip);
                if (isSingular){
                    ip = "8.8.8.8";
                }
                returnMessage= new ConnectLinuxCommand().dns(device,ip,prefix);
                json.put("status",returnMessage.getStatus());
                json.put("message",returnMessage.getMessage());
                json.put("type",prefixType);
                return json.toString();

            }else{
                return null;
            }
        }else if (prefixType.equals("Oid") || prefixType.equals("GS1") || prefixType.equals("Ecode")){
            if (ip != null){
                device device = deviceService.selectByIp1(ip);
                if (isSingular){
                    ip = "8.8.8.8";
                }
                returnMessage= new ConnectLinuxCommand().oid(device,ip,prefix);
                json.put("status",returnMessage.getStatus());
                json.put("message",returnMessage.getMessage());
                json.put("type",prefixType);
                return json.toString();

            }else{
                return null;
            }
        }
        json.put("status",1);
        json.put("message","未能查询到相应结果");
        json.put("type","未知");
        return json.toString();
    }
}
