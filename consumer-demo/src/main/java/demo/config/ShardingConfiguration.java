package demo.config;

import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sharding.IShardingConfiguration;
import sharding.config.DataSourceConfig;
import sharding.factory.ShardingConfigurationFactory;

import java.util.Collections;
import java.util.Properties;

/**
 * @author fun
 * @date 2019/6/17
 */
@Component
public class ShardingConfiguration implements IShardingConfiguration {

    @Autowired
    private DataSourceConfig dataSourceConfig;

    @Override
    public ShardingRuleConfiguration getShardingRuleConfiguration() {
        return ShardingConfigurationFactory.defaultShardingRuleConfiguration(
                dataSourceConfig.getDatasourceName(), Collections.singletonList("t_order, t_order_item"));
    }

    @Override
    public Properties getProperties() {
        return ShardingConfigurationFactory.defaultProperties();
    }
}
