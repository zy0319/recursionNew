package com.zy.recursion.dao;

import com.zy.recursion.entity.user;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface userDao {
    List<user> selectUser(user userEntity);
    List<user> selectUserForId(user userEntity);
    void addUser(user userEntity);
    void updateUser(user userEnity);
    void deleteUser(user userEnity);;
    List<user> selectAllUser();
}
