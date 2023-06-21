package org.tb.mock.schedule;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.tb.mock.constant.MockConstant;
import org.tb.mock.core.DeviceHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tb.mock.entity.Device;

/**
 * Usage: 定时任务调度器 <br/>
 * Date: 2022/11/25 12:05 <br/>
 *
 * @author <a href="mailto:lami58@outlook.lcom">hanzhang</a>
 */
@Component
@Slf4j
public class TaskSchedule {
    @Autowired
    private DeviceHelper deviceHelper;
    @Autowired
    @Qualifier(value = "d1")
    private Device d1;

    @Scheduled(cron = "0/2 * * * * ?")
    public void deviceTypeSchedule() throws MqttException {
        MqttClient mqttClient = deviceHelper.getMqttClientMap().get(d1.getToken());
        MqttMessage message = new MqttMessage();
        message.setPayload("{\"schedule\":\"yeap!\"}".getBytes());
        mqttClient.publish(MockConstant.PUBLISH, message);
    }
}
