package com.zy.recursion.Controller;


import com.zy.recursion.config.annotation;
import com.zy.recursion.service.token.loginToken;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/login", method = RequestMethod.GET)
public class login {

    @Autowired
    private loginToken loginToken;



    @CrossOrigin
    @PostMapping(value = "")
    public String cacheDelete(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        JSONObject jsonObject1 = new JSONObject();
        String username = jsonObject.getString("userName");
        String pwd = jsonObject.getString("password");
        if(username.equals("admin") && pwd.equals("123456")){
            String token = loginToken.getToken(username,pwd);
            jsonObject1.put("token", token);
            return jsonObject1.toString();

        }else
        {
            jsonObject1.put("status","1");
            return jsonObject1.toString();
        }
    }


    @annotation.UserLoginToken
    @GetMapping("/getMessage")
    public String getMessage(){
        return "1";
    }
//    @UserLoginToken
//    @GetMapping("/getMessage")
//    public String getMessage(){
//        return "你已通过验证";
//    }
}
