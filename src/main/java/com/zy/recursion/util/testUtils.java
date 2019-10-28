package com.zy.recursion.util;

import com.zy.recursion.service.device.deviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class testUtils {

    @Autowired
    deviceService deviceService;

    public static testUtils testUtils;

    @PostConstruct
    public void init() {
        testUtils = this;
    }
}
