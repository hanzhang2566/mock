package org.tb.mock;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.tb.mock.constant.MockConstant;
import org.tb.mock.core.DeviceHelper;
import org.tb.mock.entity.Device;

/**
 * Usage: 单次上报测试类 <br/>
 * Date: 2023/1/7 22:47 <br/>
 *
 * @author <a href="mailto:han.zhang@tmirob.com">hanzhang</a>
 */
@SpringBootTest
public class SingleTest {
    @Autowired
    private DeviceHelper deviceHelper;

    @Autowired
    @Qualifier(value = "d1")
    private Device d1;

    @Test
    @DisplayName("给 d1 设备发送一次数据")
    public void send() throws MqttException {
        MqttClient mqttClient = deviceHelper.getMqttClientMap().get(d1.getToken());
        MqttMessage message = new MqttMessage();
        message.setPayload("{\"SingleTest\":\"send ok!\"}".getBytes());
        mqttClient.publish(MockConstant.PUBLISH, message);
    }
}
