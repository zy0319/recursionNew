package com.zy.recursion.dao;

import com.zy.recursion.entity.token;
import org.springframework.stereotype.Repository;

@Repository
public interface tokenDao {
    void addToken(token token);
    void updateToken(token token);
}
