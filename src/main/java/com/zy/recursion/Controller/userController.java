package com.zy.recursion.Controller;

import com.zy.recursion.config.annotation;
import com.zy.recursion.entity.returnMessage;
import com.zy.recursion.entity.user;
import com.zy.recursion.service.user.userService;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;


import java.io.IOException;

@RestController
@RequestMapping(value = "/user", method = RequestMethod.GET)

public class userController {
    private static final Logger logger = LoggerFactory.getLogger(userController.class);
    @Autowired
    private user userEntity;

    @Autowired
    private userService userService;


    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/addUser")
    public returnMessage addUser(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        returnMessage returnMessage = new returnMessage();
        String userName = jsonObject.getString("user");
        String passwd = jsonObject.getString("passwd");
        //0：超级管理员；1：节点管理员；2：普通用户
        String role = jsonObject.getString("role");
        String nodeName= jsonObject.getString("nodeName");
        userEntity.setNodeName(nodeName);
        userEntity.setPasswd(passwd);
        userEntity.setRole(Integer.parseInt(role));
        userEntity.setUser(userName);
        boolean isNotExist = true;
            isNotExist = userService.checkUserIsNOTExist(userEntity);
        if (isNotExist){
            try{
                userService.addUser(userEntity);
                logger.info("add user " + userEntity.getUser()+" success");
                returnMessage.setStatus(0);
                returnMessage.setMessage("添加用户成功");
            }catch (Exception e){
                returnMessage.setStatus(1);
                returnMessage.setMessage("添加用户失败");
                e.printStackTrace();
            }
            finally {
                return returnMessage;
            }

        }
        else {
            returnMessage.setStatus(1);
            returnMessage.setMessage("该用户已存在，无法添加该用户");
        }
        return returnMessage;

    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/updateUser")
    public returnMessage updateUser(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        returnMessage returnMessage = new returnMessage();
        String id = jsonObject.getString("id");
        String userName = jsonObject.getString("user");
        String passwd = jsonObject.getString("passwd");
        //0：超级管理员；1：节点管理员；2：普通用户
        String role = jsonObject.getString("role");
        String nodeName= jsonObject.getString("nodeName");
        userEntity.setNodeName(nodeName);
        userEntity.setPasswd(passwd);
        userEntity.setRole(Integer.parseInt(role));
        userEntity.setUser(userName);
        userEntity.setId(Integer.parseInt(id));
        boolean isNotExist = true;
        isNotExist = userService.checkUserIsNOTExist(userEntity);
        if (isNotExist){
            try{
                boolean flag =userService.updateUser(userEntity);
                if (flag){
                    logger.info("update user " + userEntity.getUser()+" success");
                    returnMessage.setStatus(0);
                    returnMessage.setMessage("修改用户成功");
                }
                else{
                    logger.info("update user " + userEntity.getUser()+" failed");
                    returnMessage.setStatus(1);
                    returnMessage.setMessage("用户不存在，修改失败");
                }

            }catch (Exception e){
                returnMessage.setStatus(1);
                returnMessage.setMessage("修改用户失败");
                e.printStackTrace();
            }finally {
                return returnMessage;
            }
        }
        else{
            returnMessage.setStatus(1);
            returnMessage.setMessage("用户名已存在，无法修改成该用户");
        }
        return returnMessage;

    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/deleteUser")
    public returnMessage deleteUser(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        returnMessage returnMessage = new returnMessage();
        String id = jsonObject.getString("id");
        userEntity.setId(Integer.parseInt(id));
        try{
            boolean flag =userService.deleteUser(userEntity);
            if (flag){
                logger.info("delete user " + userEntity.getUser()+" success");
                returnMessage.setStatus(0);
                returnMessage.setMessage("删除用户成功");
            }
            else{
                logger.info("delete user " + userEntity.getUser()+" failed");
                returnMessage.setStatus(1);
                returnMessage.setMessage("用户不存在，删除失败");
            }

        }catch (Exception e){
            returnMessage.setStatus(1);
            returnMessage.setMessage("删除用户失败");
            e.printStackTrace();
        }finally {
            return returnMessage;
        }

    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/queryUser",produces = {"text/html;charset=UTF-8"})
    public String queryUser(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        int pageNum = Integer.parseInt(jsonObject.getString("pageNum"));
        int pageSize = Integer.parseInt(jsonObject.getString("pageSize"));
        return userService.queryUserAll(pageNum,pageSize);
    }
}
