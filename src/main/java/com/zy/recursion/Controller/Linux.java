package com.zy.recursion.Controller;

import com.alibaba.fastjson.JSONArray;
import com.zy.recursion.config.annotation;
import com.zy.recursion.config.linuxConfig;
import com.zy.recursion.entity.address;
import com.zy.recursion.entity.device;
import com.zy.recursion.entity.linuxMessage;
import com.zy.recursion.service.device.deviceService;
import com.zy.recursion.util.ConnectLinuxCommand;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/Linux", method = RequestMethod.GET)

public class Linux {

    @Autowired
    private address address;

    @Autowired
    private deviceService deviceService;

    @Autowired
    public ConnectLinuxCommand connectLinuxCommand;

    @Value("${threshold.diskThreshold}")
    public float diskThreshold;

    @Value("${threshold.memoryThreshold}")
    public float memoryThreshold;

    @Value("${threshold.cpuThreshold}")
    public float cpuThreshold;


    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/nodeDiskUtilization",produces = {"text/html;charset=UTF-8"})
    public String nodeDiskUtilization(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        String nodeName = jsonObject1.getString("nodeName");
        List<device> list = deviceService.selectIpByNodeName(nodeName);
        float disk = 0f;
        if (list.size() != 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nodeName",nodeName);
            for (device o : list) {
                String ip = o.getDeviceIp();
                for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
                    System.out.println(linuxMessage.toString());
                    if (linuxMessage.getDeviceIp().equals(ip)){
                        float disk1 = linuxMessage.getDiskUtilization();
                        disk = disk1 + disk;
                    }
                }
                jsonObject.put("disk_utilization", disk / list.size());
                System.out.println(jsonObject.toString());
                return jsonObject.toString();
            }
        }
        return null;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/nodeFlow",produces = {"text/html;charset=UTF-8"})
    public String nodeFlow(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        String nodeName = jsonObject1.getString("nodeName");
        List<device> list = deviceService.selectIpByNodeName(nodeName);
        float rxkb = 0f;
        float txkb = 0f;
        if (list.size() != 0) {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("nodeName",nodeName);
            for (device o : list) {
                String ip = o.getDeviceIp();
                for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
                    if (ip != null && ip.equals(linuxMessage.getDeviceIp())){
                        rxkb = rxkb + linuxMessage.getRxkB();
                        txkb = txkb + linuxMessage.getTxkB();
                    }
                }
                jsonObject2.put("txkb", txkb);
                jsonObject2.put("rxkb", rxkb);
                jsonObject2.put("bandwidth_utilization", (rxkb + txkb) / (address.getBandwidthSet() * 1024));
                System.out.println(jsonObject2.toString());
                return jsonObject2.toString();
            }
        }
        return null;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/nodeMemoryUtilization",produces = {"text/html;charset=UTF-8"})
    public String nodeMemoryUtilization(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        String nodeName = jsonObject1.getString("nodeName");
        List<device> list = deviceService.selectIpByNodeName(nodeName);
        float memory = 0f;
        if (list.size() != 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nodeName",nodeName);
            for (device o : list) {
                String ip = o.getDeviceIp();
                for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
                    if (linuxMessage.getDeviceIp().equals(ip)){
                        float memory1 = linuxMessage.getMemoryUtilization();
                        memory = memory1 + memory;
                    }
                }
                jsonObject.put("memory_utilization", memory / list.size());
                return jsonObject.toString();
            }
        }
        return null;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/nodeCpuUtilization",produces = {"text/html;charset=UTF-8"})
    public String nodeCpuUtilization(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        String nodeName = jsonObject1.getString("nodeName");
        List<device> list = deviceService.selectIpByNodeName(nodeName);
        float cpu = 0f;
        if (list.size() != 0) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("nodeName",nodeName);
            for (device o : list) {
                String ip = o.getDeviceIp();
                for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
                    if (linuxMessage.getDeviceIp().equals(ip)){
                        float cpu1 = linuxMessage.getCpuUtilization();
                        cpu = cpu1 + cpu;
                    }
                }
            }
            jsonObject.put("cpu_utilization", cpu / list.size());
            return jsonObject.toString();
        }
        return null;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/nodeData",produces = {"text/html;charset=UTF-8"})
    public String ConnectLinux(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        String nodeName = jsonObject1.getString("nodeName");
        List<device> list = deviceService.selectIpByNodeName(nodeName);
        int serious1 = 0;
        int imp1 = 0;
        int common1 = 0;
        float disk = 0f;
        float memory = 0f;
        float cpu = 0f;
        float rxkb = 0f;
        float txkb = 0f;
        int serious = 0;
        int imp = 0;
        int common = 0;
        int recursion = 0;
        int cache = 0;
        float diskThreshold1=diskThreshold;
        if (list.size() != 0) {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("nodeName",nodeName);
            for (device o : list) {
                String ip = o.getDeviceIp();
                String type = o.getDeviceType();
                InetAddress address = InetAddress.getByName(ip);
                if (type.equals("缓存")) {
                    cache++;
                } else if (type.equals("递归")) {
                    recursion++;
                }
                for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
                    if (ip != null && ip.equals(linuxMessage.getDeviceIp())){
                        float disk1 = linuxMessage.getDiskUtilization();
                        float memory1 = linuxMessage.getMemoryUtilization();
                        float cpu1 = linuxMessage.getCpuUtilization();
                        rxkb = rxkb + linuxMessage.getRxkB();
                        txkb = txkb + linuxMessage.getTxkB();

                        if (!address.isReachable(3000)){
                            if (o.getDeviceType().equals("缓存")) {
                                serious++;
                            } else {
                                serious++;
                            }
                            break;
                        }
                        if (disk1 > diskThreshold | memory1 > memoryThreshold) {
                            if (o.getDeviceType().equals("缓存")) {
                                imp++;
                            } else {
                                imp1++;
                            }
                        }
                        if (cpu1 > cpuThreshold) {
                            if (o.getDeviceType().equals("缓存")) {
                                common++;
                            } else {
                                common1++;
                            }
                        }
                    }

                }
            }
            jsonObject2.put("disk_utilization", disk / list.size());
            jsonObject2.put("memory_utilization", memory / list.size());
            jsonObject2.put("cpu_utilization", cpu / list.size());
            jsonObject2.put("serious", serious);
            jsonObject2.put("imp", imp);
            jsonObject2.put("common", common);
            jsonObject2.put("serious1", serious1);
            jsonObject2.put("imp1", imp1);
            jsonObject2.put("common1", common1);
            jsonObject2.put("cache", cache);
            jsonObject2.put("recursion", recursion);
            jsonObject2.put("txkb", txkb);
            jsonObject2.put("rxkb", rxkb);
            jsonObject2.put("bandwidth_utilization", (rxkb + txkb) / (address.getBandwidthSet() * 1024));
            return jsonObject2.toString();
        } else {
            return null;
        }
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/deviceData",produces = {"text/html;charset=UTF-8"})
    public String deviceData(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        float rxkb = 0f;
        float txkb = 0f;
        float disk = 0f;
        float memory = 0f;
        float cpu = 0f;
        String deviceIp = jsonObject1.getString("deviceIp");
        device device = deviceService.selectByIp1(deviceIp);
        for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
            if (linuxMessage.getDeviceIp().equals(deviceIp)){
                disk = linuxMessage.getDiskUtilization();
                memory = linuxMessage.getMemoryUtilization();
                cpu = linuxMessage.getCpuUtilization();
                rxkb = rxkb + linuxMessage.getRxkB();
                txkb = txkb + linuxMessage.getTxkB();
            }
        }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deviceIp",deviceIp);
            jsonObject.put("disk_utilization", disk);
            jsonObject.put("memory_utilization", memory);
            jsonObject.put("cpu_utilization", cpu);
            jsonObject.put("rxkb", rxkb);
            jsonObject.put("txkb", txkb);
            jsonObject.put("bandwidth_utilization", (rxkb + txkb) / (address.getBandwidthSet() * 1024));
            return jsonObject.toString();
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/deviceDiskUtilization",produces = {"text/html;charset=UTF-8"})
    public String deviceDiskUtilization(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        String deviceIp = jsonObject1.getString("deviceIp");
        for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
            if (linuxMessage.getDeviceIp().equals(deviceIp)){
                jsonObject1.put("disk_utilization",linuxMessage.getDiskUtilization().toString());
                return jsonObject1.toString();
            }
        }
        return null;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/deviceMemoryUtilization",produces = {"text/html;charset=UTF-8"})
    public String deviceMemoryUtilization(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        String deviceIp = jsonObject1.getString("deviceIp");
        for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
            if (linuxMessage.getDeviceIp().equals(deviceIp)){
                jsonObject1.put("memory_utilization",linuxMessage.getMemoryUtilization().toString());
                return jsonObject1.toString();
            }
        }
        return null;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/deviceCpuUtilization",produces = {"text/html;charset=UTF-8"})
    public String deviceCpuUtilization(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        String deviceIp = jsonObject1.getString("deviceIp");
        for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
            if (linuxMessage.getDeviceIp().equals(deviceIp)){
                jsonObject1.put("cpu_utilization",linuxMessage.getCpuUtilization().toString());
                return jsonObject1.toString();
            }
        }
        return null;
    }



    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/deviceFlow",produces = {"text/html;charset=UTF-8"})
    public String deviceFlow(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        String deviceIp = jsonObject1.getString("deviceIp");
        for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
            if (linuxMessage.getDeviceIp().equals(deviceIp)){
                JSONObject jsonObject = new JSONObject();
                float rxkb = linuxMessage.getRxkB();
                float txkb = linuxMessage.getTxkB();
                jsonObject.put("rxkb", rxkb);
                jsonObject.put("txkb", txkb);
                jsonObject.put("bandwidth_utilization", (rxkb + txkb) / (address.getBandwidthSet() * 1024));
                return jsonObject.toString();
            }
        }
        return null;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/nodeDataSelect",produces = {"text/html;charset=UTF-8"})
    public String nodeDataSelect(@RequestBody(required = false) String requestBody) throws IOException {
        JSONObject jsonObject1 = new JSONObject(requestBody);
        List list1 = new ArrayList();
        String nodeName = jsonObject1.getString("nodeName");
        List<device> list = deviceService.selectIpByNodeName(nodeName);
        float disk = 0f;
        float memory = 0f;
        float cpu = 0f;
        if (list.size() != 0) {
            for (device o : list) {
                JSONObject jsonObject3 = new JSONObject();
                String ip = o.getDeviceIp();
                String type = o.getDeviceType();
                List list2 = new ArrayList();
                InetAddress address = InetAddress.getByName(ip);
                for (linuxMessage linuxMessage:linuxConfig.linuxMessages){
                    if (ip != null && ip.equals(linuxMessage.getDeviceIp())){
                        float disk1 = linuxMessage.getDiskUtilization();
                        float memory1 = linuxMessage.getMemoryUtilization();
                        float cpu1 = linuxMessage.getCpuUtilization();
                        disk = disk1 + disk;
                        memory = memory1 + memory;
                        cpu = cpu1 + cpu;
                        if (!address.isReachable(3000)){
                            list2.add("服务器连接失败");
                            jsonObject3.put("status1","严重");
                            break;
                        }
                        if (cpu1 > cpuThreshold) {
                            list2.add("CPU利用率过高");
                            jsonObject3.put("status1","一般");
                        }
                        if (disk1 > diskThreshold) {
                            list2.add("硬盘利用率过高");
                            jsonObject3.put("status1","重要");
                        }
                        if (memory1 > memoryThreshold) {
                            list2.add("内存利用率过高");
                            jsonObject3.put("status1","重要");
                        }
                        if(disk1<=diskThreshold && memory1<=memoryThreshold && cpu1<=cpuThreshold){
                            list2.add("正常");
                            jsonObject3.put("status1","正常");
                        }
                    }
                }
                jsonObject3.put("deviceIp",ip);
                jsonObject3.put("type",type);
                jsonObject3.put("status",list2.toString());
                list1.add(jsonObject3);
            }
            return list1.toString();
        } else {
            return null;
        }
    }


}
