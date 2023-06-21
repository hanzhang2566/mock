package org.tb.mock.controller;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.tb.mock.constant.MockConstant;
import org.tb.mock.core.DeviceHelper;

import java.util.Objects;

/**
 * Usage: 演示设备单次上报控制器 <br/>
 * Date: 2023/1/7 20:49 <br/>
 *
 * @author <a href="mailto:han.zhang@tmirob.com">hanzhang</a>
 */
@RequestMapping(path = "device-name")
@RestController
@Slf4j
public class SingleController {
    @Autowired
    private DeviceHelper deviceHelper;

    /**
     * 演示单次上报
     *
     * @param token 设备令牌
     * @return success
     */
    @GetMapping("single")
    public String single(@RequestParam("token") String token) throws MqttException {
        MqttClient mqttClient = deviceHelper.getMqttClientMap().get(token);
        if (Objects.isNull(mqttClient)) {
            return "mqtt client is null";
        }
        MqttMessage message = new MqttMessage();
        message.setPayload("{\"project\":\"device-mock-tb\"}".getBytes());
        mqttClient.publish(MockConstant.PUBLISH, message);
        return "success";
    }
}
