//package com.zy.recursion.util;
//
//import ch.ethz.ssh2.Connection;
//import ch.ethz.ssh2.Session;
//import ch.ethz.ssh2.StreamGobbler;
//import io.micrometer.core.instrument.util.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Service;
//
//import java.io.*;
//import java.net.ConnectException;
//
//@Service
//public class asyncMethod {
//    private static final Logger logger = LoggerFactory.getLogger(ConnectLinuxCommand.class);
//    //    private static Connection conn;
////    private static Session session;
//    private static String DEFAULTCHARTSET = "UTF-8";
//
//    @Autowired
//    com.zy.recursion.util.ConnectLinuxCommand connectLinuxCommand;
//
//    //    @Async("taskExecutor")
////    public  Boolean login(String ip, String name, String password) throws IOException {
////        boolean flag = false;
////        int connectTimes = 1;
////        long waitTime = System.currentTimeMillis() + 2000;
////        do {
////            try {
////                conn = new Connection(ip);
////                conn.connect();// 连
////                break;
////            } catch (ConnectException e) {
////                return false;
////            } catch (IOException e) {
////                connectTimes++;
////                return false;
//////                e.printStackTrace();
////            }
////        } while (System.currentTimeMillis() < waitTime && 20 <= connectTimes);
////        try {
////            flag = conn.authenticateWithPassword(name, password);// 认证
////            if (flag) {
////                logger.info("认证成功！");
//////                conn.close();
////            } else {
////                logger.info("认证失败！");
////                conn.close();
////            }
////            return flag;
////        } catch (IllegalStateException e) {
////            return false;
////        }
////    }
//    @Async("taskExecutor")
//    public Connection login1(String ip, String name, String password) throws IOException {
//        boolean flag = false;
//        int connectTimes = 1;
//        Connection connection;
//        long waitTime = System.currentTimeMillis() + 2000;
//        do {
//            try {
//                connection = new Connection(ip);
//                connection.connect();// 连
//                break;
//            } catch (ConnectException e) {
//                return null;
//            } catch (IOException e) {
//                connectTimes++;
//                return null;
////                e.printStackTrace();
//            }
//        } while (System.currentTimeMillis() < waitTime && 20 <= connectTimes);
//        try {
//            flag = connection.authenticateWithPassword(name, password);// 认证
//            if (flag) {
//                logger.info("认证成功！");
////                conn.close();
//            } else {
//                logger.info("认证失败！");
//                connection.close();
//            }
//            return connection;
//        } catch (IllegalStateException e) {
//            return null;
//        }
//    }
//
//    @Async("taskExecutor")
//    public String execute(String ip, String name, String pwd, String cmd) throws IOException {
//        String result = null;
////        if (connectLinuxCommand.login(ip, name, pwd)) {
//        try {
//            Connection connection = new asyncMethod().login1(ip, name, pwd);
//            Session session = connection.openSession();
//            session.execCommand(cmd);// 执行命令
//            result = new asyncMethod().processStdout(session.getStdout(), DEFAULTCHARTSET);
//            // 如果为得到标准输出为空，说明脚本执行出错了
//            if (StringUtils.isBlank(result)) {
//                result = new asyncMethod().processStdout(session.getStderr(), DEFAULTCHARTSET);
//            }
////                    session.close();
//            connection.close();
//            session.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
////        return null;
//
//    @Async("taskExecutor")
//    public  String processStdout(InputStream in, String charset) {
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
//
//}
//
///**
// * @param in      输入流对象
// * @param charset 编码
// * @return String 以纯文本的格式返回
// * @throws
// * @Title: processStdout
// * @Description: 解析脚本执行的返回结果
// */
//
//
//
