package sharding;

import org.springframework.context.annotation.Import;
import sharding.config.DataSourceConfig;
import sharding.config.ShardingDataSourceConfig;

import java.lang.annotation.*;

/**
 * @author fun
 * @date 2019/6/14
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import({ShardingDataSourceConfig.class, DataSourceConfig.class})
public @interface EnableSharding {
}
