package com.zy.recursion.service.token.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.zy.recursion.dao.tokenDao;
import com.zy.recursion.entity.token;
import com.zy.recursion.service.token.loginToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;


@Service
public class loginTokenImpl implements loginToken {

    @Autowired
    private tokenDao tokenDao;

    public String getToken(String userName,String pwd) {
//        Date start = new Date();
//        long currentTime = System.currentTimeMillis() + 30* 60 * 1000;//一小时有效时间
//        Date end = new Date(currentTime);
        String token= "";
        token= JWT.create().withAudience(userName).sign(Algorithm.HMAC256(pwd));

//        token= JWT.create().withAudience(userName).withIssuedAt(start).withExpiresAt(end).sign(Algorithm.HMAC256(pwd));
        return token;
    }

    @Override
    public void addToken(String tokenStatus,String userName){
        token token = new token();
        token.setUserName(userName);
        token.setToken(tokenStatus);
        tokenDao.addToken(token);
    }



    @Override
    public void updateToken(String tokenStatus,String userName){
        token token = new token();
        token.setUserName(userName);
        token.setToken(tokenStatus);
        tokenDao.updateToken(token);
    }



}
