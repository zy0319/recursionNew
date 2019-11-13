package com.zy.recursion.service.user.impl;

import com.alibaba.druid.sql.parser.Lexer;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy.recursion.dao.userDao;
import com.zy.recursion.entity.user;
import org.json.JSONObject;
import com.zy.recursion.service.user.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private userDao userDao;
    @Autowired
    private user user;

    private static final Logger logger = LoggerFactory.getLogger(userServiceImpl.class);

    @Override
    public String queryUserAll(int pageNum,int pageSize) {
        List<user> list = selectALLUser();
        PageHelper.startPage(pageNum,pageSize);
        PageInfo<user> pageInfo = new PageInfo<>(userDao.selectAllUser());
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("data",pageInfo.getList());
        jsonObject1.put("pageSize",pageInfo.getPages());
        jsonObject1.put("totalCount",list.size());
        return jsonObject1.toString();
    }

    @Override
    public boolean checkUserIsNOTExist(user userEnity) {
        List<user> list = new ArrayList<user>();
        list = userDao.selectUser(userEnity);
        if (list.size() != 0){
            return false;
        }
        return true;
    }

    @Override
    public void addUser(user userEnity) {
        userDao.addUser(userEnity);
    }

    @Override
    public boolean updateUser(user userEnity) {
        user userEntityTemp = new user();
        boolean flag =true;
        userEntityTemp.setId(userEnity.getId());
        if (checkUserForId(userEntityTemp)){
            userDao.updateUser(userEnity);
        }
        else{
            flag = false;
        }
        return flag;
    }

    @Override
    public boolean deleteUser(user userEntity) {
        boolean flag = true;
        if(checkUserForId(userEntity)){
            userDao.deleteUser(userEntity);
        }
        else {
            flag = false;
        }
        return flag;

    }

    @Override
    public List<user> selectUser() {
        return null;
    }

    @Override
    public boolean checkUserAndPasswd(user userEntity) {
        if (userEntity == null || "".equals(userEntity.getPasswd()) || userEntity.getPasswd() == null ){
            return  false;
        }
        user userEntityTmep = new user();
        userEntityTmep.setUser(userEntity.getUser());
        List<user> list = userDao.selectUser(userEntityTmep);
        for(user user:list){
            if (userEntity.getPasswd().equals(user.getPasswd())){
                return true;
            }
        }
        return false;
    }



    @Override
    public user getUserByName(String userName) {
       if (userName == null ||"".equals(userName)){
           return  null;
       }

        user userEntity = new user();
       userEntity.setUser(userName);
       List<user> list = new ArrayList<user>();
       list = userDao.selectUser(userEntity);
       if (list.size() != 1){
           logger.error("user table is not right,please check it ");
           return null;
       } else {
            for(user user:list){
                return user;
            }
       }
       return null;
    }

    public boolean checkUserForId(user userEntity){
        List<user> list = new ArrayList<user>();
        list = userDao.selectUserForId(userEntity);
        if (list.size() != 1){
            return false;
        }
        return true;

    }

    public List<user> selectALLUser(){
       return userDao.selectAllUser();
    }


}
