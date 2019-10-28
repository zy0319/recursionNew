package com.zy.recursion.util;

import java.util.ArrayList;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;
import io.micrometer.core.instrument.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.*;
import java.net.ConnectException;

public class test {


    public static void main(String[] args) {
//        ListNode listNode
    }

//    private static final Logger logger = LoggerFactory.getLogger(ConnectLinuxCommand.class);
//    private static Connection conn;
//    private static Session session;
//    private static String DEFAULTCHARTSET = "UTF-8";
//
//    public static Boolean login(String ip, String name, String password) throws IOException {
//        boolean flag = false;
//        int connectTimes = 1;
//        long waitTime = System.currentTimeMillis() + 2000;
//        do {
//            try {
//                conn = new Connection(ip);
//                conn.connect();// 连
//                break;
//            } catch (ConnectException e) {
//                return false;
//            } catch (IOException e) {
//                connectTimes++;
//                return false;
////                e.printStackTrace();
//            }
//        } while (System.currentTimeMillis() < waitTime && 20 <= connectTimes);
//        try {
//            flag = conn.authenticateWithPassword(name, password);// 认证
//            if (flag) {
//                logger.info("认证成功！");
////                conn.close();
//            } else {
//                logger.info("认证失败！");
//                conn.close();
//            }
//            return flag;
//        } catch (IllegalStateException e) {
//            return false;
//        }
//    }
//
//    public static String[] execute(String ip, String name, String pwd, String[] cmd) throws IOException {
//        String[] result = new String[cmd.length];
//        if (login(ip, name, pwd)) {
//            try {
////                for (int i = 0; i < cmd.length; i++) {
//                session = conn.openSession();
//                session.execCommand("ls");// 执行命令
//                result[i] = processStdout(session.getStdout(), DEFAULTCHARTSET);
//                // 如果为得到标准输出为空，说明脚本执行出错了
//                if (StringUtils.isBlank(result[i])) {
//                    result[i] = processStdout(session.getStderr(), DEFAULTCHARTSET);
//                }
////                    session.close();
////                }
//                conn.close();
//                session.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return result;
//        }
//        return null;
//    }
//
//    public static String processStdout(InputStream in, String charset) {
//        InputStream stdout = new StreamGobbler(in);
//        StringBuffer buffer = new StringBuffer();
////        Stack<String> stack = new Stack<>();
//        try {
//            BufferedReader br = new BufferedReader(new InputStreamReader(stdout, charset));
//            String line = null;
//            while ((line = br.readLine()) != null) {
////                stack.push(line);
//                buffer.append(line + "\n");
//            }
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return buffer.toString();
//    }

}
