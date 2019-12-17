package sharding.factory;


import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import sharding.algorithm.BaseComplexKeysShardingAlgorithm;
import sharding.algorithm.TimeStampPreciseShardingAlgorithm;
import sharding.algorithm.TimeStampRangeShardingAlgorithm;
import sharding.handler.StringTimePreciseShardingHandler;
import sharding.handler.StringTimeRangeShardingHandler;
import sharding.handler.TimestampPreciseShardingHandler;
import sharding.handler.TimestampRangeShardingHandler;

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
                    shardingRuleConfiguration.getTableRuleConfigs().add(stringCreateTimeTableRuleConfiguration(datasourceName, tableName.trim()));
//                    shardingRuleConfiguration.getTableRuleConfigs().add(defaultCreateTimeTableRuleConfiguration(datasourceName, tableName.trim()));
                }
            } else {
                shardingRuleConfiguration.getTableRuleConfigs().add(defaultCreateTimeTableRuleConfiguration(datasourceName, tableNames.trim()));
            }
            shardingRuleConfiguration.getBindingTableGroups().add(tableNames);
        }
        return shardingRuleConfiguration;
    }

    public static TableRuleConfiguration defaultCreateTimeTableRuleConfiguration(String datasourceName, String tableName) {
        TableRuleConfiguration tableRule = new TableRuleConfiguration(tableName, String.format("%s.%s_$->{19..20}$->{10..12}", datasourceName, tableName));
        tableRule.setTableShardingStrategyConfig(
                new StandardShardingStrategyConfiguration("create_time", new TimeStampPreciseShardingAlgorithm(), new TimeStampRangeShardingAlgorithm()));
        return tableRule;
    }

    public static TableRuleConfiguration stringCreateTimeTableRuleConfiguration(String datasourceName, String tableName) {
        TableRuleConfiguration tableRule = new TableRuleConfiguration(tableName,
                String.format("%s.%s_$->{19..19}$->{11..12},%s.%s_$->{20..20}0$->{1..2}",
                        datasourceName, tableName, datasourceName, tableName));
        ComplexKeyShadingHandlerBuilder builder = ComplexKeyShadingHandlerBuilder.builder()
                .addHandler("create_time", new TimestampPreciseShardingHandler())
                .addHandler("create_time", new TimestampRangeShardingHandler())
                .addHandler("create_time", new StringTimePreciseShardingHandler())
                .addHandler("create_time", new StringTimeRangeShardingHandler())
                .build();
        tableRule.setTableShardingStrategyConfig(
                new ComplexShardingStrategyConfiguration("create_time", new BaseComplexKeysShardingAlgorithm(builder)));
        return tableRule;
    }

    public static Properties defaultProperties() {
        return new ShardingPropertiesBuilder()
                .showSql(true)
                .build();
    }

}
