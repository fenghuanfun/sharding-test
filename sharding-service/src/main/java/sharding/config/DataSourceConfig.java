package sharding.config;

import lombok.Data;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author fun
 * @date 2019/6/12
 */
@Data
@SpringBootConfiguration
@ConfigurationProperties(prefix = "spring.sharding-jdbc")
public class DataSourceConfig {

    private String datasourceName;

    private String url;

    private String type;

    private String driverClassName;

    private String username;

    private String password;

}
