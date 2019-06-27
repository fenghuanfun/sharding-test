package sharding.factory;


import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import sharding.algorithm.BaseComplexKeysShardingAlgorithm;

import java.util.List;
import java.util.Properties;

/**
 * @author fun
 * @date 2019/6/14
 */
public class ShardingConfigurationFactory {

    public final static String SPILT = ",";

    public static ShardingRuleConfiguration defaultShardingRuleConfiguration(String datasourceName, List<String> tableNamesList) {
        ShardingRuleConfiguration shardingRuleConfiguration = new ShardingRuleConfiguration();
        for (String tableNames : tableNamesList) {
            if (tableNames.contains(SPILT)) {
                for (String tableName : StringUtils.split(tableNames, SPILT)) {
                    shardingRuleConfiguration.getTableRuleConfigs().add(defaultCreateTimeTableRuleConfiguration(datasourceName, tableName.trim()));
                }
            } else {
                shardingRuleConfiguration.getTableRuleConfigs().add(defaultCreateTimeTableRuleConfiguration(datasourceName, tableNames.trim()));
            }
            shardingRuleConfiguration.getBindingTableGroups().add(tableNames);
        }
        return shardingRuleConfiguration;
    }

    public static TableRuleConfiguration defaultCreateTimeTableRuleConfiguration(String datasourceName, String tableName) {
        TableRuleConfiguration tableRule = new TableRuleConfiguration(tableName, String.format("%s.%s_$->{19..19}0$->{1..6}", datasourceName, tableName));
//        tableRule.setTableShardingStrategyConfig(
//                new StandardShardingStrategyConfiguration("create_time", new TimeStampPreciseShardingAlgorithm(), new TimeStampRangeShardingAlgorithm()));
        tableRule.setTableShardingStrategyConfig(
                new ComplexShardingStrategyConfiguration("id,create_time", new BaseComplexKeysShardingAlgorithm()));
        return tableRule;
    }

    public static Properties defaultProperties() {
        return new ShardingPropertiesBuilder()
                .showSql(true)
                .build();
    }

}
