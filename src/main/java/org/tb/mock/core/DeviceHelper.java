package org.tb.mock.core;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tb.mock.callback.DeviceTypeMqttCallback;
import org.tb.mock.constant.MockConstant;
import org.tb.mock.entity.Device;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Usage: 初始化设备的 mqtt client，并装配对应设备的 client 和 callback <br/>
 * Date: 2022/11/24 18:15 <br/>
 *
 * @author <a href="mailto:lami58@outlook.lcom">hanzhang</a>
 */
@Slf4j
@Component
public class DeviceHelper implements InitializingBean {
    /**
     * 配置在系统中的设备
     */
    @Autowired
    private List<Device> devices;

    /**
     * key：token
     * value：token 对应的 MqttClient
     */
    @Getter
    private Map<String, MqttClient> mqttClientMap;

    /**
     * 根据每个 device 生成一个对应 mqttClient<br>
     * 每个 mqttClient 设置自己的 callback 处理
     */
    @Override
    public void afterPropertiesSet() {
        log.info("devices = {}", devices);
        mqttClientMap = new HashMap<>(devices.size());
        // 初始化 mqtt client，并保存在 mqttClientMap 中
        devices.forEach(this::initMqttClient);
        // 初始化每个 mqtt client 的 callback
        devices.forEach(device -> {
            MqttClient mqttClient = mqttClientMap.get(device.getToken());
            // 将返回的 callback 装配到 mqttClient 中
            mqttClient.setCallback(getCallback(device));
        });
    }

    /**
     * 初始化 mqtt client
     *
     * @param device 设备信息
     */
    private void initMqttClient(Device device) {
        try {
            String tbUrl = String.format("tcp://%s:%s", MockConstant.IP, MockConstant.PORT);
            MqttClient mqttClient = new MqttClient(tbUrl, MqttClient.generateClientId(), new MemoryPersistence());

            // 配置 mqttClient 参数
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(true);
            options.setUserName(device.getToken());
            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(30);
            options.setAutomaticReconnect(true);
            mqttClient.connect(options);

            // 添加订阅
            mqttClient.subscribe(MockConstant.SUBSCRIBE);
            // 保存 token - mqttClient
            mqttClientMap.put(device.getToken(), mqttClient);
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据设备类型返回实现了的 callback
     *
     * @param device 设备信息
     * @return 设备 callback
     */
    private MqttCallback getCallback(Device device) {
        switch (device.getDeviceType()) {
            case DEVICE_TYPE:
                return new DeviceTypeMqttCallback(mqttClientMap.get(device.getToken()));
            default:
                log.warn("no impl callback");
        }
        return null;
    }
}
