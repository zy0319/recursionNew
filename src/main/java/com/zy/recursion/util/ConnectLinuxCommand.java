package com.zy.recursion.util;

import java.io.*;
import java.net.ConnectException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import com.zy.recursion.entity.device;
import com.zy.recursion.entity.returnMessage;
import io.micrometer.core.instrument.util.StringEscapeUtils;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConnectLinuxCommand {


    private static final Logger logger = LoggerFactory.getLogger(ConnectLinuxCommand.class);
    private static String[] deviceIps;
    private static Connection[] connections;
    private static Session session;
    private static String DEFAULTCHARTSET = "UTF-8";

    @Autowired
    com.zy.recursion.service.device.deviceService deviceService;

    public static Boolean login(String ip, String name, String password) throws IOException {
        boolean flag = false;
        int connectTimes = 1;
        long waitTime = System.currentTimeMillis() + 2000;
        Connection conn;
        do {
            try {
                conn = new Connection(ip);
                conn.connect();// 连
                break;
            } catch (ConnectException e) {
                return false;
            } catch (IOException e) {
                connectTimes++;
                return false;
//                e.printStackTrace();
            }
        } while (System.currentTimeMillis() < waitTime && 20 <= connectTimes);
        try {
            flag = conn.authenticateWithPassword(name, password);// 认证
            if (flag) {
                logger.info("认证成功！");
                conn.close();
            } else {
                logger.info("认证失败！");
                conn.close();
            }
            return flag;
        } catch (IllegalStateException e) {
            return false;
        }
    }


    public static void login1(List<device> list) throws IOException {
//        List<device> list = testUtils.testUtils.deviceService.selectAll1();
        connections = new Connection[list.size()];
        deviceIps = new String[list.size()];
        int n = 0;
        for (device device:list) {
            String deviceIp = device.getDeviceIp();
            boolean flag = false;
            int connectTimes = 1;
            long waitTime = System.currentTimeMillis() + 2000;
            Connection conn = new Connection(device.getDeviceIp());
            do {
                try {
                    conn.connect();// 连
                    break;
                } catch (ConnectException e) {
//                    return null;
                } catch (IOException e) {
                    connectTimes++;
//                    return null;
//                e.printStackTrace();
                }
            } while (System.currentTimeMillis() < waitTime && 20 <= connectTimes);
            try {
                flag = conn.authenticateWithPassword(device.getDeviceUserName(),device.getDevicePwd());// 认证
                if (flag) {
                    connections[n] = conn;
                    deviceIps[n] = deviceIp;
                    logger.info("认证成功！");
                    n++;
//                conn.close();
                } else {
                    logger.info("认证失败！");
                    conn.close();
                    n++;
                }
//                return conn;
            } catch (IllegalStateException e) {
//                return null;
            }
        }
    }

    public static String[] execute(String ip,String[] cmd) throws IOException {
        String[] result = new String[cmd.length];
        int m = 0;
        for (int i = 0; i < deviceIps.length; i++) {
            if (ip.equals(deviceIps[i])){
                m=i;
                break;
            }
        }
//        Connection connection = login1(ip, name, pwd);
        if (connections[m] != null) {
            try {
                for (int i = 0; i < cmd.length; i++) {
                    Session session = null;
                    session = connections[m].openSession();
                    session.execCommand(cmd[i]);// 执行命令
                    result[i] = processStdout(session.getStdout(), DEFAULTCHARTSET);
                    // 如果为得到标准输出为空，说明脚本执行出错了
                    if (StringUtils.isBlank(result[i])) {
                        result[i] = processStdout(session.getStderr(), DEFAULTCHARTSET);
                    }
                    session.close();
                }
//                conn.close();
//                assert session != null;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return result;
        }
        return null;
    }

    /**
     * @param in      输入流对象
     * @param charset 编码
     * @return String 以纯文本的格式返回
     * @throws
     * @Title: processStdout
     * @Description: 解析脚本执行的返回结果
     */
    public static String processStdout(InputStream in, String charset) {
        InputStream stdout = new StreamGobbler(in);
        StringBuffer buffer = new StringBuffer();
//        Stack<String> stack = new Stack<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
            String line = null;
            while ((line = br.readLine()) != null) {
//                stack.push(line);
                buffer.append(line + "\n");
            }
            br.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer.toString();
    }

    /**
     * @param
     * @Description:通过@Async注解表明该方法是一个异步方法， 如果注解在类级别上，则表明该类所有的方法都是异步方法，而这里的方法自动被注入使用ThreadPoolTaskExecutor作为TaskExecutor
     * @Title: executeAysncTask1
     * @Date: 2018年9月21日 下午2:54:32
     * @Author: OnlyMate
     * @Throws
     */

//    @Async
    public Float disk_utilization(String result) throws IOException {
        int used = 0;
        long available = 0;
        Stack stack = new Stack();
//        if (ConnectLinuxCommand.execute(ip, name, pwd, "df -k") != null) {
//            BufferedReader br = new BufferedReader(new StringReader(ConnectLinuxCommand.execute(ip, name, pwd, "df -k")));
        BufferedReader br = new BufferedReader(new StringReader(result));
        String line = null;
        while ((line = br.readLine()) != null) {
            stack.push(line);
        }
        br.close();
        for (int i = 1; i < stack.size(); i++) {
            String a = stack.pop().toString();
            used = used + Integer.parseInt(a.split("\\s+")[2]);
            available = available + Long.parseLong(a.split("\\s+")[3]);
        }
        DecimalFormat df = new DecimalFormat("0.0000");
        Float disk_utilization = Float.valueOf(df.format((float) used / (available + used)));
        System.out.println("disk"+disk_utilization);
        return disk_utilization;
//        } else {
//            return null;
//        }
    }

    //    @Async
    public Float memory_utilization(String result) throws IOException {
        Stack stack1 = new Stack();
//        BufferedReader br1 = new BufferedReader(new StringReader(ConnectLinuxCommand.execute(ip, name, pwd, "sar -r 1 1")));
        BufferedReader br1 = new BufferedReader(new StringReader(result));
        String line1 = null;
        while ((line1 = br1.readLine()) != null) {
            stack1.push(line1);
        }
        br1.close();
        String memory = stack1.pop().toString();
        Float memory_utilization = Float.valueOf(memory.split("\\s+")[3]);
        System.out.println("memory_utilization"+memory_utilization);
        return memory_utilization;
    }

    //    @Async
    public Float cpu_utilization(String result) throws IOException {
        Stack stack2 = new Stack();
        BufferedReader br3 = new BufferedReader(new StringReader(result));
//        BufferedReader br2 = new BufferedReader(new StringReader(ConnectLinuxCommand.execute(ip, name, pwd, "sar -u 1 1")));
        String line2 = null;
        while ((line2 = br3.readLine()) != null) {
            stack2.push(line2);
        }
        br3.close();
        String cpu = stack2.pop().toString();
        DecimalFormat df1 = new DecimalFormat("0.00");
        Float cpu_utilization = 100 - Float.valueOf(cpu.split("\\s+")[7]);
        System.out.println("cpu_utilization"+cpu_utilization);
        return cpu_utilization;
    }


    //    @Async
    public JSONObject networkCard(String result, String networkCard) throws IOException {
        Stack stack2 = new Stack();
        JSONObject jsonObject = new JSONObject();
        BufferedReader br2 = new BufferedReader(new StringReader(result));
//        BufferedReader br2 = new BufferedReader(new StringReader(ConnectLinuxCommand.execute(ip, name, pwd, "sar -n DEV 1 1")));
        String line2 = null;
        while ((line2 = br2.readLine()) != null) {
            stack2.push(line2);
        }
        br2.close();
        for (int i = 0; i < stack2.size(); i++) {
            String[] mes = stack2.pop().toString().split("\\s+");
            if (mes.length != 1) {
                if (mes[1].equals(networkCard) && mes[0].contains(":")) {
                    System.out.println(mes[1]);
                    jsonObject.put("rxkB", mes[4]);
                    jsonObject.put("txkB", mes[5]);
                    System.out.println(jsonObject.toString());
                    return jsonObject;
                }
            }
        }
        return null;
    }


    public String logRead(List<device> list, String address) throws IOException {
        Stack stack2 = new Stack();
        int handle  = 0;
        int dns  = 0;
        int oid  = 0;
        int ecode  = 0;
        int gs1  = 0;
        int receive = 0;
        int drop = 0;
        int reply = 0;
        int avg_rep = 0;
        int success = 0;
        int other = 0;
        float success_rate = 0;
        float hit_rate = 0;
        int recur = 0;
        int avg_recur = 0;
        float recur_success = 0;
        float all_receive = 0;
        String total_time = "无";
        String time = "0";
        int cacheDeviceCount = 0;
        int recursionDeviceCount = 0;
        String nodeName = list.get(0).getNodeName();
        if (list.size() != 0) {
            for (device o : list) {
                if (o.getDeviceType().equals("缓存")) {
                    cacheDeviceCount++;
                    String[] cmd = {"tail -2 " + address};
                    String[] result = ConnectLinuxCommand.execute(o.getDeviceIp(), cmd);
                    BufferedReader br2 = new BufferedReader(new StringReader(result[0]));
                    String line2;
                    while ((line2 = br2.readLine()) != null) {
                        stack2.push(line2);
                    }
                    br2.close();
                    String[] splitAddress = stack2.pop().toString().split("\\|");
                    if (splitAddress[0].contains(":")) {
                        time = splitAddress[0].substring(1, 9);
                        receive = Integer.parseInt(splitAddress[0].substring(10).trim()) + receive;
                        handle = Integer.parseInt(splitAddress[1].trim()) + handle;
                        dns = Integer.parseInt(splitAddress[2].trim()) + dns;
                        oid = Integer.parseInt(splitAddress[3].trim()) + oid;
                        ecode = Integer.parseInt(splitAddress[4].trim()) + ecode;
                        gs1 = Integer.parseInt(splitAddress[5].trim()) + gs1;
                        drop = Integer.parseInt(splitAddress[6].trim()) + drop;
                        reply = Integer.parseInt(splitAddress[7].trim()) + reply;
                        avg_rep = Integer.parseInt(splitAddress[8].substring(0, splitAddress[8].length() - 2).trim()) + avg_rep;
                        success = Integer.parseInt(splitAddress[9].trim()) + success;
                        other = Integer.parseInt(splitAddress[10].trim()) + other;
                        success_rate = Float.parseFloat(splitAddress[11].substring(0, splitAddress[11].length() - 1).trim()) + success_rate;
                        hit_rate = Float.parseFloat(splitAddress[12].substring(0, splitAddress[12].length() - 1).trim()) + hit_rate;
                        recur = Integer.parseInt(splitAddress[13].trim()) + recur;
                        avg_recur = Integer.parseInt(splitAddress[14].substring(0, splitAddress[14].length() - 2).trim()) + avg_recur;
                        recur_success = Float.parseFloat(splitAddress[15].substring(0, splitAddress[15].length() - 1).trim()) + recur_success;
                        all_receive = Float.parseFloat(splitAddress[16].trim()) + all_receive;
                        total_time = splitAddress[17];
                        String text = total_time.substring(6, 18);
                        String condition = text.substring(2, 3) + text.substring(5, 6) + text.substring(8, 9) + text.substring(11, 12);
                        System.out.println(condition);
                        switch (condition) {
                            case "Dhms":
                                total_time = text.substring(0, 2).trim() + "天" + text.substring(3, 5).trim() + "小时" + text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                                break;
                            case "MDhm":
                                total_time = text.substring(0, 2).trim() + "月" + text.substring(3, 5).trim() + "天" + text.substring(6, 8).trim() + "小时" + text.substring(9, 11).trim() + "分钟";
                                break;
                            case "YMDh":
                                total_time = text.substring(0, 2).trim() + "年" + text.substring(3, 5).trim() + "月" + text.substring(6, 8).trim() + "天" + text.substring(9, 11).trim() + "小时";
                                break;
                            case " hms":
                                total_time = text.substring(3, 5).trim() + "小时" + text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                                break;
                            case "  ms":
                                total_time = text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                                break;
                            case "   s":
                                total_time = text.substring(9, 11).trim() + "秒";
                                break;
                        }
                    } else {
                        String[] splitAddress1 = stack2.pop().toString().split("\\|");
                        time = splitAddress1[0].substring(1, 9);
                        receive = Integer.parseInt(splitAddress1[0].substring(10).trim()) + receive;
                        handle = Integer.parseInt(splitAddress1[1].trim()) + handle;
                        dns = Integer.parseInt(splitAddress1[2].trim()) + dns;
                        oid = Integer.parseInt(splitAddress1[3].trim()) + oid;
                        ecode = Integer.parseInt(splitAddress1[4].trim()) + ecode;
                        gs1 = Integer.parseInt(splitAddress1[5].trim()) + gs1;
                        drop = Integer.parseInt(splitAddress1[6].trim()) + drop;
                        reply = Integer.parseInt(splitAddress1[7].trim()) + reply;
                        avg_rep = Integer.parseInt(splitAddress1[8].substring(0, splitAddress[8].length() - 2).trim()) + avg_rep;
                        success = Integer.parseInt(splitAddress1[9].trim()) + success;
                        other = Integer.parseInt(splitAddress1[10].trim()) + other;
                        success_rate = Float.parseFloat(splitAddress1[11].substring(0, splitAddress[11].length() - 1).trim()) + success_rate;
                        hit_rate = Float.parseFloat(splitAddress1[12].substring(0, splitAddress[12].length() - 1).trim()) + hit_rate;
                        recur = Integer.parseInt(splitAddress1[13].trim()) + recur;
                        avg_recur = Integer.parseInt(splitAddress1[14].substring(0, splitAddress[14].length() - 2).trim()) + avg_recur;
                        recur_success = Float.parseFloat(splitAddress1[15].substring(0, splitAddress[15].length() - 1).trim()) + recur_success;
                        all_receive = Float.parseFloat(splitAddress1[16].trim()) + all_receive;
                        total_time = splitAddress1[17];
                        String text = total_time.substring(6, 18);
                        String condition = text.substring(2, 3) + text.substring(5, 6) + text.substring(8, 9) + text.substring(11, 12);
                        System.out.println(condition);
                        switch (condition) {
                            case "Dhms":
                                total_time = text.substring(0, 2).trim() + "天" + text.substring(3, 5).trim() + "小时" + text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                                break;
                            case "MDhm":
                                total_time = text.substring(0, 2).trim() + "月" + text.substring(3, 5).trim() + "天" + text.substring(6, 8).trim() + "小时" + text.substring(9, 11).trim() + "分钟";
                                break;
                            case "YMDh":
                                total_time = text.substring(0, 2).trim() + "年" + text.substring(3, 5).trim() + "月" + text.substring(6, 8).trim() + "天" + text.substring(9, 11).trim() + "小时";
                                break;
                            case " hms":
                                total_time = text.substring(3, 5).trim() + "小时" + text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                                break;
                            case "  ms":
                                total_time = text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                                break;
                            case "   s":
                                total_time = text.substring(9, 11).trim() + "秒";
                                break;
                        }
                    }
                } else {
                    recursionDeviceCount++;
                }
            }
            JSONObject jsonObject = new JSONObject();
            if (cacheDeviceCount != 0) {
                jsonObject.put("AVG_REP_TIME", avg_rep / cacheDeviceCount);
                jsonObject.put("SUCCESS_RATE", success_rate / cacheDeviceCount);
                jsonObject.put("AVG_RECUR_TIME", avg_recur / cacheDeviceCount);
                jsonObject.put("RECUR_SUCCESS", recur_success / cacheDeviceCount);
                jsonObject.put("HIT_RATE", hit_rate / cacheDeviceCount);
            } else {
                jsonObject.put("AVG_REP_TIME", avg_rep);
                jsonObject.put("SUCCESS_RATE", success_rate);
                jsonObject.put("AVG_RECUR_TIME", avg_recur);
                jsonObject.put("RECUR_SUCCESS", recur_success);
                jsonObject.put("HIT_RATE", hit_rate);
            }
            jsonObject.put("HANDLE", handle);
            jsonObject.put("DNS", dns);
            jsonObject.put("OID", oid);
            jsonObject.put("ECODE", ecode);
            jsonObject.put("GS1", gs1);
            jsonObject.put("TIME", time);
            jsonObject.put("RECEIVE", receive);
            jsonObject.put("DROP", drop);
            jsonObject.put("REPLY", reply);
            jsonObject.put("SUCCESS", success);
            jsonObject.put("OTHER", other);
            jsonObject.put("RECUR", recur);
            jsonObject.put("ALL_RECEIVE", all_receive);
            jsonObject.put("total_time", total_time);
            jsonObject.put("nodeName", nodeName);
            System.out.println(jsonObject.toString());
            return jsonObject.toString();
        }
        return null;
    }

    public JSONObject logRead1(device o, String address) throws IOException {
        Stack stack2 = new Stack();
        int handle  = 0;
        int dns  = 0;
        int oid  = 0;
        int ecode  = 0;
        int gs1  = 0;
        int receive = 0;
        int drop = 0;
        int reply = 0;
        int avg_rep = 0;
        int success = 0;
        int other = 0;
        float success_rate = 0;
        float hit_rate = 0;
        int recur = 0;
        int avg_recur = 0;
        float recur_success = 0;
        float all_receive = 0;
        String total_time = null;
        String time = null;
        String[] cmd = {"tail -2 " + address};
        String[] result = ConnectLinuxCommand.execute(o.getDeviceIp(), cmd);
        BufferedReader br2 = new BufferedReader(new StringReader(result[0]));
        String line2 = null;
        while ((line2 = br2.readLine()) != null) {
            stack2.push(line2);
        }
        br2.close();
        String[] splitAddress = stack2.pop().toString().split("\\|");
        if (splitAddress[0].contains(":")) {
            time = splitAddress[0].substring(1, 9);
            receive = Integer.parseInt(splitAddress[0].substring(10).trim()) + receive;
            handle = Integer.parseInt(splitAddress[1].trim()) + handle;
            dns = Integer.parseInt(splitAddress[2].trim()) + dns;
            oid = Integer.parseInt(splitAddress[3].trim()) + oid;
            ecode = Integer.parseInt(splitAddress[4].trim()) + ecode;
            gs1 = Integer.parseInt(splitAddress[5].trim()) + gs1;
            drop = Integer.parseInt(splitAddress[6].trim()) + drop;
            reply = Integer.parseInt(splitAddress[7].trim()) + reply;
            avg_rep = Integer.parseInt(splitAddress[8].substring(0, splitAddress[8].length() - 2).trim()) + avg_rep;
            success = Integer.parseInt(splitAddress[9].trim()) + success;
            other = Integer.parseInt(splitAddress[10].trim()) + other;
            success_rate = Float.parseFloat(splitAddress[11].substring(0, splitAddress[11].length() - 1).trim()) + success_rate;
            hit_rate = Float.parseFloat(splitAddress[12].substring(0, splitAddress[12].length() - 1).trim()) + hit_rate;
            recur = Integer.parseInt(splitAddress[13].trim()) + recur;
            avg_recur = Integer.parseInt(splitAddress[14].substring(0, splitAddress[14].length() - 2).trim()) + avg_recur;
            recur_success = Float.parseFloat(splitAddress[15].substring(0, splitAddress[15].length() - 1).trim()) + recur_success;
            all_receive = Float.parseFloat(splitAddress[16].trim()) + all_receive;
            total_time = splitAddress[17];
            String text = total_time.substring(6, 18);
            String condition = text.substring(2, 3) + text.substring(5, 6) + text.substring(8, 9) + text.substring(11, 12);
            System.out.println(condition);
            switch (condition) {
                case "Dhms":
                    total_time = text.substring(0, 2).trim() + "天" + text.substring(3, 5).trim() + "小时" + text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                    break;
                case "MDhm":
                    total_time = text.substring(0, 2).trim() + "月" + text.substring(3, 5).trim() + "天" + text.substring(6, 8).trim() + "小时" + text.substring(9, 11).trim() + "分钟";
                    break;
                case "YMDh":
                    total_time = text.substring(0, 2).trim() + "年" + text.substring(3, 5).trim() + "月" + text.substring(6, 8).trim() + "天" + text.substring(9, 11).trim() + "小时";
                    break;
                case " hms":
                    total_time = text.substring(3, 5).trim() + "小时" + text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                    break;
                case "  ms":
                    total_time = text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                    break;
                case "   s":
                    total_time = text.substring(9, 11).trim() + "秒";
                    break;
            }
        } else {
            String[] splitAddress1 = stack2.pop().toString().split("\\|");
            time = splitAddress1[0].substring(1, 9);
            receive = Integer.parseInt(splitAddress1[0].substring(10).trim()) + receive;
            handle = Integer.parseInt(splitAddress1[1].trim()) + handle;
            dns = Integer.parseInt(splitAddress1[2].trim()) + dns;
            oid = Integer.parseInt(splitAddress1[3].trim()) + oid;
            ecode = Integer.parseInt(splitAddress1[4].trim()) + ecode;
            gs1 = Integer.parseInt(splitAddress1[5].trim()) + gs1;
            drop = Integer.parseInt(splitAddress1[6].trim()) + drop;
            reply = Integer.parseInt(splitAddress1[7].trim()) + reply;
            avg_rep = Integer.parseInt(splitAddress1[8].substring(0, splitAddress[8].length() - 2).trim()) + avg_rep;
            success = Integer.parseInt(splitAddress1[9].trim()) + success;
            other = Integer.parseInt(splitAddress1[10].trim()) + other;
            success_rate = Float.parseFloat(splitAddress1[11].substring(0, splitAddress[11].length() - 1).trim()) + success_rate;
            hit_rate = Float.parseFloat(splitAddress1[12].substring(0, splitAddress[12].length() - 1).trim()) + hit_rate;
            recur = Integer.parseInt(splitAddress1[13].trim()) + recur;
            avg_recur = Integer.parseInt(splitAddress1[14].substring(0, splitAddress[14].length() - 2).trim()) + avg_recur;
            recur_success = Float.parseFloat(splitAddress1[15].substring(0, splitAddress[15].length() - 1).trim()) + recur_success;
            all_receive = Float.parseFloat(splitAddress1[16].trim()) + all_receive;
            total_time = splitAddress1[17];
            String text = total_time.substring(6, 18);
            String condition = text.substring(2, 3) + text.substring(5, 6) + text.substring(8, 9) + text.substring(11, 12);
            System.out.println(condition);
            switch (condition) {
                case "Dhms":
                    total_time = text.substring(0, 2).trim() + "天" + text.substring(3, 5).trim() + "小时" + text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                    break;
                case "MDhm":
                    total_time = text.substring(0, 2).trim() + "月" + text.substring(3, 5).trim() + "天" + text.substring(6, 8).trim() + "小时" + text.substring(9, 11).trim() + "分钟";
                    break;
                case "YMDh":
                    total_time = text.substring(0, 2).trim() + "年" + text.substring(3, 5).trim() + "月" + text.substring(6, 8).trim() + "天" + text.substring(9, 11).trim() + "小时";
                    break;
                case " hms":
                    total_time = text.substring(3, 5).trim() + "小时" + text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                    break;
                case "  ms":
                    total_time = text.substring(6, 8).trim() + "分钟" + text.substring(9, 11).trim() + "秒";
                    break;
                case "   s":
                    total_time = text.substring(9, 11).trim() + "秒";
                    break;
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("TIME", time);
        jsonObject.put("RECEIVE", receive);
        jsonObject.put("DROP", drop);
        jsonObject.put("REPLY", reply);
        jsonObject.put("AVG_REP_TIME", avg_rep);
        jsonObject.put("SUCCESS", success);
        jsonObject.put("OTHER", other);
        jsonObject.put("SUCCESS_RATE", success_rate);
        jsonObject.put("HIT_RATE", hit_rate);
        jsonObject.put("RECUR", recur);
        jsonObject.put("AVG_RECUR_TIME", avg_recur);
        jsonObject.put("RECUR_SUCCESS", recur_success);
        jsonObject.put("ALL_RECEIVE", all_receive);
        jsonObject.put("total_time", total_time);
        jsonObject.put("nodeName", o.getNodeName());
        jsonObject.put("deviceIp", o.getDeviceIp());
        jsonObject.put("deviceType",o.getDeviceType());
        jsonObject.put("HANDLE", handle);
        jsonObject.put("DNS", dns);
        jsonObject.put("OID", oid);
        jsonObject.put("ECODE", ecode);
        jsonObject.put("GS1", gs1);
        return jsonObject;
    }

    public returnMessage dns(device device, String ip, String prefix) throws IOException {
        returnMessage returnMessage = new returnMessage();

        String[] cmd = {"dig " + "@" + "172.171.1.80" + " " + prefix};
        String[] result = ConnectLinuxCommand.execute(device.getDeviceIp(), cmd);
        Stack stack = new Stack();
        JSONObject jsonObject = new JSONObject();
        BufferedReader br2 = new BufferedReader(new StringReader(result[0]));
        String line2 = null;
        while (!(line2 = br2.readLine()).equals(";; AUTHORITY SECTION:")) {
            stack.push(line2);
        }
        br2.close();
        stack.pop();
        String m = stack.pop().toString();
        String a = "";
        System.out.println(a);
        if (!m.contains(";")){
            stack.push(m);
            List list = new ArrayList();
            while (!(a = stack.pop().toString()).equals(";; ANSWER SECTION:")) {
                String b = a.split("\\s+")[3];
                if (b.equals("A")) {
                    list.add(a.split("\\s+")[4]);
                }
            }
            returnMessage.setStatus(0);
            returnMessage.setMessage(list.toString());
            return returnMessage;
        }else {
            returnMessage.setStatus(1);
            returnMessage.setMessage("解析失败");
            return returnMessage;
        }
    }


    public returnMessage oid(device device, String ip, String prefix) throws IOException {
        returnMessage returnMessage = new returnMessage();
        String[] cmd = {"dig " + "@" + "172.171.1.80" + " " + prefix + " NAPTR"};
        String[] result = ConnectLinuxCommand.execute(device.getDeviceIp(), cmd);
        Stack stack = new Stack();
        JSONObject jsonObject = new JSONObject();
        BufferedReader br2 = new BufferedReader(new StringReader(result[0]));
        String line2 = null;
        while (!(line2 = br2.readLine()).equals(";; AUTHORITY SECTION:")) {
            stack.push(line2);
        }
        br2.close();
        stack.pop();
        String m = stack.pop().toString();
//        System.out.println(m);
        if (!m.contains(";")){
            stack.push(m);
            String a = "";
            while (!(a = stack.pop().toString()).equals(";; ANSWER SECTION:")) {
                String key1 = a.split("\\s+")[7];
                String key2 = a.split("\\s+")[9];
                jsonObject.put(key1.substring(1,key1.length()-1),key2.substring(0,key2.length()-1));
            }
            returnMessage.setStatus(0);
            returnMessage.setMessage(jsonObject.toString());
            return returnMessage;
        } else {
            returnMessage.setStatus(1);
            returnMessage.setMessage("解析失败");
            return returnMessage;
        }
    }


    public static void main(String[] args) throws IOException {
//
//        String[] cmd = new String[]{"tail -2 /home/fnii/handle_cache/log/handle_cache_stats.1"};
//        System.out.println(ConnectLinuxCommand.execute("172.171.1.80","root","pms123handle$%^",cmd)[0]);

    }
}




