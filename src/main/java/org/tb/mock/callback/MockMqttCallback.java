package org.tb.mock.callback;

import org.eclipse.paho.client.mqttv3.MqttCallbackExtended;

/**
 * Usage: mqtt 回调接口，所有设备的 mqtt client 回调处理都需要实现该接口 <br/>
 * Date: 2022/11/24 17:52 <br/>
 *
 * @author <a href="mailto:lami58@outlook.lcom">hanzhang</a>
 * @see MqttCallbackExtended
 */
public interface MockMqttCallback extends MqttCallbackExtended {
}
