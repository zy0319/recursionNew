package com.zy.recursion.Controller;

import com.zy.recursion.config.annotation;
import com.zy.recursion.entity.device;
import com.zy.recursion.entity.node;
import com.zy.recursion.service.device.deviceService;
import com.zy.recursion.service.node.nodeService;
import com.zy.recursion.util.ConnectLinuxCommand;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.zy.recursion.entity.returnMessage;


@RestController
@RequestMapping(value = "/Node", method = RequestMethod.GET)
public class NodeController {

    @Autowired
    private nodeService nodeService;

    @Autowired
    private deviceService deviceService;

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/register")
    public returnMessage addNode(@RequestBody(required = false) String requesyBody) {
        JSONObject jsonObject = new JSONObject(requesyBody);
        returnMessage returnMessage = new returnMessage();
        if (jsonObject.has("nodeName") && jsonObject.has("operatorName")) {
            if (!jsonObject.getString("nodeName").equals("") && !jsonObject.getString("operatorName").equals("")) {
                if (nodeService.selectByNodename(jsonObject.getString("nodeName"))) {
                    node node = new node();
                    node.setNodeName(jsonObject.getString("nodeName"));
                    node.setDeviceCount(0);
                    node.setOperatorName(jsonObject.getString("operatorName"));
                    nodeService.addNode(node);
                    returnMessage.setStatus(0);
                    returnMessage.setMessage("注册成功");
                    return returnMessage;
                } else {
                    returnMessage.setStatus(1);
                    returnMessage.setMessage("地区名字不能重复");
                    return returnMessage;
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
    public List<node> selectAll() {
        List<node> list = nodeService.selectAll();
        return list;
    }


    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/selectAllData")
    public List selectAllData() throws IOException {
        List<node> list = nodeService.selectAll();
        List list2 = new ArrayList();
        for (node m : list) {
            if (m.getDeviceCount() != 0) {
                List<device> list1 = deviceService.selectIpByNodeName(m.getNodeName());
                int serious = 0;
                int imp = 0;
                int common = 0;
                int serious1 = 0;
                int imp1 = 0;
                int common1 = 0;
                for (device o : list1) {
                    String ip = o.getDeviceIp();
                    String name = o.getDeviceUserName();
                    String pwd = o.getDevicePwd();
                    String[] cmd = {"df -k", "sar -r 1 1", "sar -u 1 1"};
                    String[] result = ConnectLinuxCommand.execute(ip, cmd);
                    if (result != null) {
                        float disk1 = new ConnectLinuxCommand().disk_utilization(result[0]);
                        float memory1 = new ConnectLinuxCommand().memory_utilization(result[1]);
                        float cpu1 = new ConnectLinuxCommand().cpu_utilization(result[2]);
                        if (disk1 > 0.9 | memory1 > 90) {
                            if (o.getDeviceType().equals("缓存")) {
                                imp++;
                            } else {
                                imp1++;
                            }
                        }
                        if (cpu1 > 90) {
                            if (o.getDeviceType().equals("缓存")) {
                                common++;
                            } else {
                                common1++;
                            }
                        }
                        if (serious + serious1 > 0) {
                            m.setNodeStatus(0);
                        } else if (imp + imp1 > 0 || common + common1 > 0) {
                            m.setNodeStatus(1);
                        } else {
                            m.setNodeStatus(2);
                        }
                    } else {
                        m.setNodeStatus(0);
                    }
                }
            } else {
                m.setNodeStatus(3);
            }
            list2.add(m.getNodeStatus());
        }
        return list2;
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/delete")
    public returnMessage deleteNode(@RequestBody(required = false) String requesyBody) {
        JSONObject jsonObject = new JSONObject(requesyBody);
        returnMessage returnMessage = new returnMessage();
        if (jsonObject.has("nodeName")) {
            nodeService.deleteNode(jsonObject.getString("nodeName"));
            deviceService.deleteDeviceByNodename(jsonObject.getString("nodeName"));
            returnMessage.setStatus(0);
            returnMessage.setMessage("删除成功");
            return returnMessage;
        } else {
            returnMessage.setStatus(1);
            returnMessage.setMessage("参数错误");
            return returnMessage;
        }
    }

    @annotation.UserLoginToken
    @CrossOrigin
    @PostMapping(value = "/update")
    public returnMessage updateNode(@RequestBody(required = false) String requesyBody) {
        JSONObject jsonObject = new JSONObject(requesyBody);
        node node = new node();
        returnMessage returnMessage = new returnMessage();
        if (jsonObject.has("preNodeName") && jsonObject.has("newNodeName") && jsonObject.has("operatorName")) {
            if (!jsonObject.getString("preNodeName").equals("") && !jsonObject.getString("newNodeName").equals("") && !jsonObject.getString("operatorName").equals("")) {
                if (jsonObject.getString("preNodeName").equals(jsonObject.getString("newNodeName"))) {
                    node = nodeService.selectNodeByNodename(jsonObject.getString("preNodeName"));
                    node.setOperatorName(jsonObject.getString("operatorName"));
                    nodeService.updateNode(node);
                    returnMessage.setStatus(0);
                    returnMessage.setMessage("success");
                    return returnMessage;
                } else {
                    if (nodeService.selectByNodename(jsonObject.getString("newNodeName"))) {
                        node = nodeService.selectNodeByNodename(jsonObject.getString("preNodeName"));
                        node.setOperatorName(jsonObject.getString("operatorName"));
                        node.setNodeName(jsonObject.getString("newNodeName"));
                        nodeService.updateNode(node);
                        returnMessage.setStatus(0);
                        returnMessage.setMessage("success");
                        return returnMessage;
                    } else {
                        returnMessage.setStatus(1);
                        returnMessage.setMessage("地区名字不能重复");
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
}
