package org.tb.mock.entity;

import org.tb.mock.enums.DeviceType;
import lombok.Builder;
import lombok.Data;

/**
 * Usage: 设备实体，每个 tb 中的设备需要一个对应的 device 实例 <br/>
 * Date: 2022/11/24 17:56 <br/>
 *
 * @author <a href="mailto:lami58@outlook.lcom">hanzhang</a>
 */
@Data
@Builder
public class Device {
    /**
     * 设备令牌<br>
     * 唯一标识一个设备
     */
    private String token;

    /**
     * 设备类型<br>
     * tb 中选择的设备配置
     */
    private DeviceType deviceType;
}
