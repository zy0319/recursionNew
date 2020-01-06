package com.zy.recursion.config;

import ch.ethz.ssh2.Connection;
import com.zy.recursion.entity.address;
import com.zy.recursion.entity.device;
import com.zy.recursion.entity.handleCache;
import com.zy.recursion.entity.linuxMessage;
import com.zy.recursion.service.handleCache.handleCacheService;
import com.zy.recursion.util.ConnectLinuxCommand;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@EnableScheduling   // 2.开启定时任务
public class linuxConfig  {

//    public static linuxMessage[] linuxMessages;
//    public static handleCache[] handleCaches;

    public static Map<String, linuxMessage> linuxMessageMap = new ConcurrentHashMap<String, linuxMessage>();
    public static Map<String, handleCache> handleCachesMap = new ConcurrentHashMap<String, handleCache>();

    @Autowired
    private com.zy.recursion.service.device.deviceService deviceService;

    @Autowired
    private com.zy.recursion.entity.address address;


    @Autowired
    private com.zy.recursion.service.handleCache.handleCacheService handleCacheService;

    private static final Logger logger = LoggerFactory.getLogger(linuxConfig.class);
    @PostConstruct
    public void init() throws IOException {
        //系统启动中。。。加载codeMap
        List<device> list = deviceService.selectAll1();
//        handleCaches = new handleCache[list.size()];
//        linuxMessages = new linuxMessage[list.size()];
        ConnectLinuxCommand.login1(list);

    }

    public void LinuxMessage() throws IOException {
        //系统启动中。。。加载codeMap
        List<device> list = deviceService.selectAll1();
        int n = 0;
        for (device device:list){
            String ip = device.getDeviceIp();
            InetAddress inetaddress = InetAddress.getByName(ip);
            if (!inetaddress.isReachable(3000)){
                logger.info("ip:"+ip +" 连接失败");
                return;
            }

            String[] cmd = new String[]{"df -k","sar -n DEV 1 1","sar -r 1 1","sar -u 1 1"};//硬盘、流量、内存、cpu
            String[] result = ConnectLinuxCommand.execute(device.getDeviceIp(),cmd);
            if (result == null){
                return;
            }
            Float diskUtilization = new ConnectLinuxCommand().disk_utilization(result[0]);
            JSONObject flow = new ConnectLinuxCommand().networkCard(result[1],address.getNetworkCard());
            Float memoryUtilization = new ConnectLinuxCommand().memory_utilization(result[2]);
            Float cpuUtilization = new ConnectLinuxCommand().cpu_utilization(result[3]);
            linuxMessage linuxMessage = new linuxMessage();
            if (flow != null){
                linuxMessage.setRxkB(Float.valueOf(flow.getString("rxkB")));
                linuxMessage.setTxkB(Float.valueOf(flow.getString("txkB")));
                linuxMessage.setHandle_cache_stats(flow.toString());
            }
            else{
                linuxMessage.setRxkB(2.5f);
                linuxMessage.setTxkB(5.5f);
            }
            linuxMessage.setCpuUtilization(cpuUtilization);
            linuxMessage.setDiskUtilization(diskUtilization);
            linuxMessage.setMemoryUtilization(memoryUtilization);
            linuxMessage.setDeviceIp(device.getDeviceIp());
            linuxMessageMap.put(device.getDeviceIp(),linuxMessage);
//            linuxMessages[n] = linuxMessage;
//            n = n+1;
        }
    }

    public void LinuxHandleCache() throws IOException {
        //系统启动中。。。加载codeMap
        List<device> list = deviceService.selectAll1();
        int n = 0;
        for (device device:list){
            String ip = device.getDeviceIp();
            InetAddress inetaddress = InetAddress.getByName(ip);
            JSONObject result = new JSONObject();
            if (inetaddress.isReachable(3000)){
                result = new ConnectLinuxCommand().logRead1(device,address.getAddress());
                handleCache handleCache = new handleCache();
                handleCache.setHandleCache(result);
                handleCachesMap.put(device.getDeviceIp(),handleCache);
            }

//            handleCaches[n] = handleCache;
//            n = n+1;
        }
    }

    public void addHandleCache() throws IOException {
        //系统启动中。。。加载codeMap
        Iterator<Map.Entry<String, handleCache>> entries = handleCachesMap.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, handleCache> entry = entries.next();
            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            handleCache handleCache = entry.getValue();
            if (handleCache != null){
                handleCacheService.addHandleCache(handleCache);
            }

//            //注意这里操作了集合,下面的的遍历不会再打印0
//            if("0".equals(entry.getKey())) {
//                map.remove(entry.getKey());
//            }
//
//        for (handleCache handleCache : handleCaches) {
//            handleCacheService.addHandleCache(handleCache);
//        }
        }
    }


//
//    @PreDestroy
//    public void destroy() {
//        //系统运行结束
//    }

//    @Scheduled(cron = "0 0 0/2 * * ?")
    @Scheduled(cron = "${address.javaRefresh}")
    @DependsOn
    @Async("taskExecutor")
    public void testOne() throws IOException {
        //每2小时执行一次缓存
        LinuxMessage();
//        LinuxHandleCache();
//        addHandleCache();
    }

    @Scheduled(cron = "${address.javaRefresh}")
    @DependsOn
    @Async("taskExecutor")
    public void testOne1() throws IOException {
        //每2小时执行一次缓存
//        LinuxMessage();
        LinuxHandleCache();
//        addHandleCache();
    }


    @Scheduled(cron = "${address.javaRefresh1}")
    @DependsOn
    @Async("taskExecutor")
    public void testOne2() throws IOException {
        //每2小时执行一次缓存
//        LinuxMessage();
//        LinuxHandleCache();
        addHandleCache();
    }


}
