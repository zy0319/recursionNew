package com.zy.recursion.Controller;

import com.zy.recursion.config.annotation;
import com.zy.recursion.entity.device;
import com.zy.recursion.entity.node;
import com.zy.recursion.entity.returnMessage;
import com.zy.recursion.service.device.*;
import com.zy.recursion.service.node.nodeService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;
import com.zy.recursion.util.ConnectLinuxCommand;



@RestController
@RequestMapping(value = "/Device", method = RequestMethod.GET)
public class deviceController {

    @Autowired
    private deviceService deviceService;

    @Autowired
    private nodeService nodeService;

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/register")
    public returnMessage addDevice(@RequestBody(required = false) String requesyBody) throws IOException {
        JSONObject jsonObject = new JSONObject(requesyBody);
        device device = new device();
        node node = new node();
        returnMessage returnMessage = new returnMessage();
        if (jsonObject.has("nodeName") && jsonObject.has("deviceName") && jsonObject.has("deviceIp") && jsonObject.has("deviceLocation") && jsonObject.has("deviceType") && jsonObject.has("deviceUserName") && jsonObject.has("devicePwd")) {
            if (!jsonObject.getString("nodeName").equals("") && !jsonObject.getString("deviceName").equals("") && !jsonObject.getString("deviceIp").equals("") && !jsonObject.getString("deviceLocation").equals("") && !jsonObject.getString("deviceType").equals("")&& !jsonObject.getString("deviceUserName").equals("")&& !jsonObject.getString("devicePwd").equals("")) {
                if (nodeService.selectByNodename(jsonObject.getString("nodeName"))) {
                    returnMessage.setStatus(1);
                    returnMessage.setMessage("没有该节点");
                    return returnMessage;
                } else {
                    if (deviceService.selectByIp(jsonObject.getString("deviceIp"))) {
                        if(ConnectLinuxCommand.login(jsonObject.getString("deviceIp"),jsonObject.getString("deviceUserName"),jsonObject.getString("devicePwd"))){
                            device.setNodeName(jsonObject.getString("nodeName"));
                            device.setDeviceName(jsonObject.getString("deviceName"));
                            device.setDeviceIp(jsonObject.getString("deviceIp"));
                            device.setDeviceLocation(jsonObject.getString("deviceLocation"));
                            device.setDeviceType(jsonObject.getString("deviceType"));
                            device.setDeviceUserName(jsonObject.getString("deviceUserName"));
                            device.setDevicePwd(jsonObject.getString("devicePwd"));
                            deviceService.addDevice(device);
                            node = nodeService.selectNodeByNodename(jsonObject.getString("nodeName"));
                            node.setDeviceCount(node.getDeviceCount() + 1);
                            nodeService.updateNode(node);
                            returnMessage.setStatus(0);
                            returnMessage.setMessage("注册成功");
                            return returnMessage;
                        }else {
                            returnMessage.setStatus(1);
                            returnMessage.setMessage("服务器连接失败");
                            return returnMessage;
                        }
                    } else {
                        returnMessage.setStatus(1);
                        returnMessage.setMessage("ip不能重复");
                        return returnMessage;
                    }
                }
            } else {
                returnMessage.setStatus(1);
                returnMessage.setMessage("参数内容不能为空");
                return returnMessage;
            }
        } else {
            returnMessage.setStatus(1);
            returnMessage.setMessage("参数错误");
            return returnMessage;
        }
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/selectAll")
    public List<device> selectAll() {
        List<device> list = deviceService.selectAll();
        return list;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/selectDeviceByNodename")
    public List selectDeviceByNodename(@RequestBody(required = false) String requesyBody) {
        JSONObject jsonObject = new JSONObject(requesyBody);
        List<device> list = deviceService.selectDeviceByNodeName(jsonObject.getString("nodeName"));
        return list;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/selectDeviceByIp",produces = {"text/html;charset=UTF-8"})
    public String selectDeviceByIp(@RequestBody(required = false) String requesyBody) {
        JSONObject jsonObject = new JSONObject(requesyBody);
        String type = jsonObject.getString("deviceIp");
        device device = deviceService.selectDeviceByIp(jsonObject.getString("deviceIp"));
        return device.getDeviceType();
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/selectIp")
    public List selectIp(@RequestBody(required = false) String requesyBody) {
        JSONObject jsonObject = new JSONObject(requesyBody);
        List list = deviceService.selectIpByNodeName(jsonObject.getString("nodeName"));
        for (Object o : list) {
            System.out.println(o);
        }
        return list;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/delete")
    public returnMessage deletedevice(@RequestBody(required = false) String requesyBody) {
        JSONObject jsonObject = new JSONObject(requesyBody);
        returnMessage returnMessage = new returnMessage();
        node node = new node();
        if (jsonObject.has("nodeName") && jsonObject.has("deviceIp")) {
            if (!jsonObject.getString("nodeName").equals("") && !jsonObject.getString("deviceIp").equals("")) {
                System.out.println(jsonObject.getString("deviceIp"));
                deviceService.deleteDevice(jsonObject.getString("deviceIp"));
                node = nodeService.selectNodeByNodename(jsonObject.getString("nodeName"));
                node.setDeviceCount(node.getDeviceCount() - 1);
                nodeService.updateNode(node);
                returnMessage.setStatus(0);
                returnMessage.setMessage("删除成功");
                return returnMessage;
            } else {
                returnMessage.setStatus(1);
                returnMessage.setMessage("参数不能为空");
                return returnMessage;
            }
        } else {
            returnMessage.setStatus(1);
            returnMessage.setMessage("参数错误");
            return returnMessage;
        }
    }
    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/update")
    public returnMessage updatedevice(@RequestBody(required = false) String requesyBody) {
        JSONObject jsonObject = new JSONObject(requesyBody);
        device device = new device();
        returnMessage returnMessage = new returnMessage();
        if (jsonObject.has("preDeviceIp") && jsonObject.has("deviceName") && jsonObject.has("newDeviceIp") && jsonObject.has("deviceLocation") && jsonObject.has("deviceType")) {
            if (!jsonObject.getString("preDeviceIp").equals("") && !jsonObject.getString("deviceName").equals("") && !jsonObject.getString("newDeviceIp").equals("") && !jsonObject.getString("deviceLocation").equals("") && !jsonObject.getString("deviceType").equals("")) {
                device = deviceService.selectDeviceByIp(jsonObject.getString("preDeviceIp"));
                System.out.println(device.getDeviceIp()+device.getDeviceLocation());
                if (!device.getDeviceIp().equals(jsonObject.getString("newDeviceIp"))) {
                    if (deviceService.selectByIp(jsonObject.getString("newDeviceIp"))) {
                        device.setDeviceName(jsonObject.getString("deviceName"));
                        device.setDeviceLocation(jsonObject.getString("deviceLocation"));
                        device.setDeviceIp(jsonObject.getString("newDeviceIp"));
                        device.setDeviceType(jsonObject.getString("deviceType"));
                        deviceService.updateDevice(device);
                        returnMessage.setStatus(0);
                        returnMessage.setMessage("修改成功");
                        return returnMessage;
                    }else {
                        returnMessage.setStatus(1);
                        returnMessage.setMessage("ip重复");
                        return returnMessage;
                    }
                } else {
                    device.setDeviceName(jsonObject.getString("deviceName"));
                    device.setDeviceLocation(jsonObject.getString("deviceLocation"));
                    device.setDeviceIp(jsonObject.getString("preDeviceIp"));
                    device.setDeviceType(jsonObject.getString("deviceType"));
                    deviceService.updateDevice(device);
                    returnMessage.setStatus(0);
                    returnMessage.setMessage("修改成功");
                    return returnMessage;
                }
            } else {
                returnMessage.setStatus(1);
                returnMessage.setMessage("参数不能为空");
                return returnMessage;
            }
        } else {
            returnMessage.setStatus(1);
            returnMessage.setMessage("参数错误");
            return returnMessage;
        }
    }
}
