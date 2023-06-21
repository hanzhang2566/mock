package org.tb.mock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.tb.mock.constant.MockConstant;

import java.util.Objects;

/**
 * Usage: device-mock 服务启动类 <br/>
 * Date: 2022/11/24 17:52 <br/>
 *
 * @author <a href="mailto:lami58@outlook.lcom">hanzhang</a>
 */
@SpringBootApplication
@Slf4j

public class DeviceMockApplication {
    public static void main(String[] args) {
        if (Objects.equals("", MockConstant.IP)) {
            log.error("TB IP 未配置，启动失败");
            return;
        }

        if (Objects.equals("", MockConstant.PORT)) {
            log.error("TB PORT 未配置，启动失败");
            return;
        }

        if (Objects.equals("", MockConstant.SUBSCRIBE)) {
            log.error("TB SUBSCRIBE 未配置，启动失败");
            return;
        }

        if (Objects.equals("", MockConstant.PUBLISH)) {
            log.error("TB PUBLISH 未配置，启动失败");
            return;
        }
        SpringApplication.run(DeviceMockApplication.class, args);
    }
}
