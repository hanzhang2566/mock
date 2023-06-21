package org.tb.mock.config;

import org.tb.mock.entity.Device;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.tb.mock.enums.DeviceType;

/**
 * Usage: 设备信息配置类 <br/>
 * Date: 2022/11/24 20:33 <br/>
 *
 * @author <a href="mailto:lami58@outlook.lcom">hanzhang</a>
 */
@Configuration
public class DeviceConfig {
    /**
     * 注入 device-name 设备到 ioc 中
     *
     * @return 设备信息
     */
    @Bean("d1")
    public Device deviceType() {
        return Device.builder().token("pCQdDLglDUQaFfFpP1sv").deviceType(DeviceType.DEVICE_TYPE).build();
    }
}

