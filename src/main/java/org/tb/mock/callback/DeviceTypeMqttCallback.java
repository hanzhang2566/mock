package org.tb.mock.callback;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.tb.mock.constant.MockConstant;

import java.util.concurrent.TimeUnit;

/**
 * Usage: 演示设备的 mqtt callback <br/>
 * Date: 2023/1/7 20:14 <br/>
 *
 * @author <a href="mailto:han.zhang@tmirob.com">hanzhang</a>
 */
@Slf4j
public class DeviceTypeMqttCallback implements MockMqttCallback {
    private final MqttClient mqttClient;

    public DeviceTypeMqttCallback(MqttClient mqttClient) {
        this.mqttClient = mqttClient;
    }

    /**
     * 当消息从服务器到达设备时，调用此方法<br>
     * 此方法的任何异常都会导致 client 被关闭<br>
     * 更多信息参看该方法的 javaDoc
     *
     * @param topic   发布消息的主题
     * @param message 服务器发来的消息
     * @throws Exception Exception
     */
    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        // 接收到 tb 的命令
        log.info("topic = {}, msg = {}", topic, message);

        // 模拟处理过程，并返回对应数据
        TimeUnit.SECONDS.sleep(2L);
        message.setPayload("{\"result\":\"success\"}".getBytes());
        mqttClient.publish(MockConstant.PUBLISH, message);
    }

    /**
     * 当消息传递到 tb，并且所有确认消息已收到时，该方法会被调用
     * <ul>
     *     <li>对于 qos0 的消息，消息一旦传递到网络上，该方法会被调用</li>
     *     <li>对于 qos1 的消息，当收到 PUBACK 时，该方法会被调用</li>
     *     <li>对于 qos2 的消息，当收到 PUBCOMP 时，该方法会被调用</li>
     * </ul>
     * 发送和接收的 token 都是相同的
     *
     * @param token 设备令牌
     */
    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        log.info("DeviceTypeMqttCallback.deliveryComplete");
    }

    /**
     * 当与 tb 的连接完成时，该方法会被执行
     *
     * @param reconnect 如果自动连接了，该参数值是 true
     * @param serverUrl 连接地址
     */
    @Override
    public void connectComplete(boolean reconnect, String serverUrl) {
        log.info("DeviceTypeMqttCallback.connectComplete. serverUrl = {}, reconnect  = {}", serverUrl, reconnect);
    }

    /**
     * 当 tb 服务失去连接后，该方法会被执行
     *
     * @param cause 失去连接的原因
     */
    @Override
    public void connectionLost(Throwable cause) {
        log.info("DeviceTypeMqttCallback.connectionLost。 cause = {}", cause);
    }
}
