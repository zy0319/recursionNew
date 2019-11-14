package com.zy.recursion.Controller;


import com.zy.recursion.config.annotation;
import com.zy.recursion.entity.user;
import com.zy.recursion.service.token.loginToken;
import com.zy.recursion.service.user.userService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;

@RestController
@RequestMapping(value = "/login", method = RequestMethod.GET)
public class login {

    @Autowired
    private loginToken loginToken;

    @Autowired
    private userService userService;

    @Autowired
    private user user;




    @CrossOrigin
    @PostMapping(value = "",produces = {"text/html;charset=UTF-8"})
    public String loginAction(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        JSONObject jsonObject1 = new JSONObject();
        String username = jsonObject.getString("userName");
        String encondePwd = new BASE64Encoder().encode(jsonObject.getString("password").getBytes());
        user.setUser(username);
        user.setPasswd(encondePwd);
        if(userService.checkUserAndPasswd(user)){
            user userEntity = userService.getUserByName(username);
            byte[] bytes = new BASE64Decoder().decodeBuffer(encondePwd);
            String pwd = new String(bytes, "UTF-8");
            String token = loginToken.getToken(username,pwd);
            jsonObject1.put("token", token);
            jsonObject1.put("role",String.valueOf(userEntity.getRole()));
            jsonObject1.put("node",userEntity.getNodeName());
            return jsonObject1.toString();
        }
//        if(username.equals("admin") && pwd.equals("123456")){
//            String token = loginToken.getToken(username,pwd);
//            jsonObject1.put("token", token);
//            return jsonObject1.toString();
//
//        }
        else
        {
            jsonObject1.put("status","登录失败");
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
