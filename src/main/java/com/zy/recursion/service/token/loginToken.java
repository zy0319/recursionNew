package com.zy.recursion.service.token;

public interface loginToken {

    String getToken(String userName,String pwd);
    void addToken(String token,String userName);
    void updateToken(String token,String userName);

}
