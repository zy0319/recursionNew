package com.zy.recursion.util;

import com.zy.recursion.entity.returnMessage;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URISyntaxException;

public class httpPost {



    public static String  sendPostDataByJson(String url, String json, String encoding) throws ClientProtocolException, IOException {
        String result = "";
        // 创建httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // 创建post方式请求对象
        HttpPost httpPost = new HttpPost(url);
        // 设置参数到请求对象中
        StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
//        stringEntity.setContentEncoding("utf-8");
        httpPost.setEntity(stringEntity);
        // 执行请求操作，并拿到结果（同步阻塞）
        CloseableHttpResponse response = httpClient.execute(httpPost);
//        result = EntityUtils.toString(response.getEntity(), "utf-8");
        result = EntityUtils.toString(response.getEntity());// 返回json格式：
//        Logger logger=Logger.getLogger("=gnj");;
//        logger.info("POST请求返回的数据是："+responsejson);
        // 获取结果实
        // 判断网络连接状态码是否正常(0--200都数正常)
//        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
//            result = EntityUtils.toString(response.getEntity(), "utf-8");
//        }
        // 释放链接
        response.close();
        return result;
    }

    public static String  sendGetDataByJson(String url,String json, String encoding) throws ClientProtocolException, IOException {
        String result = "";
        CloseableHttpResponse response = null;
        // 创建httpclient对象
        try{
            CloseableHttpClient httpClient = HttpClients.createDefault();
            URIBuilder uriBuilder = new URIBuilder(url);
            //uriBuilder.addParameter("prefix",prefix );
            // 根据带参数的URI对象构建GET请求对象
            HttpGet httpGet = new HttpGet(uriBuilder.build());
            httpGet.addHeader("Content-Type","application/x-javascript;charset=utf-8");
            response = httpClient.execute(httpGet);
            // result = EntityUtils.toString(response.getEntity());
            String body = EntityUtils.toString(response.getEntity());
            JSONObject responseBody = new JSONObject(body);
            result = responseBody.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            response.close();
        }

        return result;


    }

    public static returnMessage testSendPostDataByJson(JSONObject jsonObject) throws ClientProtocolException, IOException {
        String ip = jsonObject.getString("ip");
        String URL = "http://"+ip+":8088/analysisapi/";
        returnMessage returnMessage = new returnMessage();
        returnMessage.setMessage(sendPostDataByJson(URL, jsonObject.toString(), "utf-8"));
        return returnMessage;
    }

    public static returnMessage testSendGetDataByJson(JSONObject jsonObject) throws ClientProtocolException, IOException, URISyntaxException {
        String ip = jsonObject.getString("ip");
        String URL = "http://"+ip+":8080/api/handles/";
        String prefix = jsonObject.getString("prefix");
        String getUrl = URL+prefix;
        returnMessage returnMessage = new returnMessage();
        returnMessage.setMessage(sendGetDataByJson(getUrl, jsonObject.toString(), "utf-8"));
        return returnMessage;
    }

//    public static void main(String[] args) {
//        System.out.println(testSendPostDataByJson());
//    }
}
