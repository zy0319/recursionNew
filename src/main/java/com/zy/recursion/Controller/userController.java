package com.zy.recursion.Controller;

import com.zy.recursion.config.annotation;
import com.zy.recursion.entity.returnMessage;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping(value = "/user", method = RequestMethod.GET)
public class userController {

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/addUser")
    public returnMessage addUser(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        returnMessage returnMessage = new returnMessage();
        String user = jsonObject.getString("user");
        String passwd = jsonObject.getString("passwd");
        String role = jsonObject.getString("role");
        String nodeName= jsonObject.getString("nodeName");


        return returnMessage;
    }
}
