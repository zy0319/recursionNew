package com.zy.recursion.service.user;

import com.zy.recursion.entity.user;

import java.util.List;

public interface userService {
    String queryUserAll(int pageNum,int pageSize);
    boolean checkUserIsNOTExist(user userEnity);
    void addUser(user userEnity);
    boolean updateUser(user userEnity);
    boolean deleteUser(user userEnity);
    List<user> selectUser();
    boolean checkUserAndPasswd(user userEntity);
    user getUserByName(String userName);


}
