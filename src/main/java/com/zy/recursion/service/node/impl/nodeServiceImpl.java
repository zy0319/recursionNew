package com.zy.recursion.service.node.impl;
import com.zy.recursion.dao.nodeDao;
import com.zy.recursion.entity.node;
import com.zy.recursion.service.node.nodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class nodeServiceImpl implements nodeService {

    @Autowired
    private nodeDao nodeDao;

    @Override
    public void addNode(node node){
        nodeDao.addNode(node);
    }

    @Override
    public List<node> selectAll(){
        return nodeDao.selectAll();
    }

    @Override
    public void deleteNode(String nodeName){
        nodeDao.deleteNode(nodeName);
    }

    @Override
    public void updateNode(node node){
        nodeDao.updateNode(node);
    }

    @Override
    public Integer selectDevice(String nodeName){
        return nodeDao.selectDevice(nodeName);
    }

    @Override
    public boolean selectByNodename(String nodeName){
        return nodeDao.selectByNodename(nodeName) == null;
    }

    @Override
    public int selectCountByNodename(String nodeName){
        return nodeDao.selectByNodename(nodeName).getDeviceCount();
    }

    @Override
    public node selectNodeByNodename(String nodeName){
        return nodeDao.selectByNodename(nodeName);
    }



}
