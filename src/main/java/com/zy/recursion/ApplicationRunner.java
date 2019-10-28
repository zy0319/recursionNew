//package com.zy.recursion;
//
//import com.zy.recursion.util.ConnectLinuxCommand;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ApplicationRunner implements CommandLineRunner {
//
//    @Override
//    public void run(String... args) throws Exception {
//        //此处为了记录一下线程的使用，没任何意义
//        new Thread(() -> {
//            try {
//                execute();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        ).start();
//    }
//
//    private void execute() throws Exception {
//        ConnectLinuxCommand.login1();
//    }
//}
