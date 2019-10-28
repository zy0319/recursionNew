//package com.zy.recursion.util;
//import com.alibaba.fastjson.JSONArray;
//import org.json.JSONObject;
//import sun.tools.jstat.Jstat;
//
//import java.io.*;
//import java.util.Stack;
//
//public class testReadFile {
//    public static void main(String[] args) throws IOException {
//        long start = System.currentTimeMillis();
//        File file = new File("File/handle_cache_stats");
//        BufferedReader br = new BufferedReader(new FileReader(file));
//        Stack<String> stack = new Stack<>();
//        try {
//            String line = br.readLine();
//            //一行一行读
//            while (line != null) { //按行读数据
//                stack.push(line);
////                System.out.println(line.charAt(0));
////                System.out.println(line);
////                String[] splitAddress=line.split("\\|"); //如果以竖线为分隔符，则split的时候需要加上两个斜杠【\\】进行转义
////                System.out.println(splitAddress[0].substring(10).trim()+splitAddress[1].trim()+splitAddress[2].trim()+splitAddress[3].trim());
//                line = br.readLine();
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            //最后一定要关闭
//            try {
//                br.close();
//                br.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            long end = System.currentTimeMillis();//结束时间
//            JSONObject jsonObject1 = new JSONObject();
//            JSONObject jsonObject2 = new JSONObject();
//            JSONArray jsonArray = new JSONArray();
//
//            int j = 1;
//            for (int i = 0; i < 13; i++) {
//                String[] splitAddress=stack.pop().split("\\|"); //如果以竖线为分隔符，则split的时候需要加上两个斜杠【\\】进行转义
////                System.out.println(splitAddress[0].trim()+splitAddress[1].trim()+splitAddress[2].trim()+splitAddress[3].trim());
//                if(splitAddress[0].contains(":")){
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.put("TIME",splitAddress[0].substring(1,9));
//                    jsonObject.put("RECEIVE",splitAddress[0].substring(10).trim());
//                    jsonObject.put("DROP",splitAddress[1].trim());
//                    jsonObject.put("REPLY",splitAddress[2].trim());
//                    jsonObject.put("AVG_REP_TIME",splitAddress[3].substring(0,splitAddress[3].length()-2).trim());
//                    jsonObject.put("SUCCESS",splitAddress[4].trim());
//                    jsonObject.put("OTHER",splitAddress[5].trim());
//                    jsonObject.put("SUCCESS RATE",splitAddress[6].substring(0,splitAddress[6].length()-1).trim());
//                    jsonObject.put("HIT_RATE",splitAddress[7].substring(0,splitAddress[7].length()-1).trim());
//                    jsonObject.put("RECUR",splitAddress[8].trim());
//                    jsonObject.put("AVG_RECUR_TIME",splitAddress[9].substring(0,splitAddress[9].length()-2).trim());
//                    jsonObject.put("RECUR_SUCCESS",splitAddress[10].substring(0,splitAddress[10].length()-1).trim());
////                    jsonArray.add(jsonObject);
//                    jsonObject1.put("data"+j,jsonObject);
//                    j++;
////                    jsonArray.add(jsonObject);
//                }
//            }
////            jsonArray.add(jsonObject1);
////            jsonObject2.put("data",jsonArray);
//            System.out.println(jsonObject1);
////            System.out.println(jsonObject2);
//            System.out.println("传统IO读取数据，不指定缓冲区大小，总共耗时：" + (end - start) + "ms");
//
//        }
//    }
//}
