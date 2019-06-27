package sharding;

import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;

import java.util.Properties;

/**
 * @author fun
 * @date 2019/6/14
 */
public interface IShardingConfiguration {

    /**
     * 分片配置
     *
     * @return
     */
    ShardingRuleConfiguration getShardingRuleConfiguration();

    /**
     * 额外参数配置
     *
     * @return
     */
    Properties getProperties();

}
