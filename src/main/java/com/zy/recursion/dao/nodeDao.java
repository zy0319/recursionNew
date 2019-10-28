package com.zy.recursion.dao;
import com.zy.recursion.entity.node;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface nodeDao {
    void addNode(node node);
    List<node> selectAll();
    void deleteNode(String nodeName);
    void updateNode(node node);
    Integer selectDevice(String nodeName);
    node selectByNodename(String nodeName);
}
