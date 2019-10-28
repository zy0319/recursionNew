package com.zy.recursion.service.node;

import com.zy.recursion.entity.node;

import java.util.List;

public interface nodeService {
    void addNode(node node);
    List<node> selectAll();
    void deleteNode(String nodeName);
    void updateNode(node node);
    Integer selectDevice(String nodeName);
    boolean selectByNodename(String nodeName);
    int selectCountByNodename(String nodeName);
    node selectNodeByNodename(String nodeName);


}
