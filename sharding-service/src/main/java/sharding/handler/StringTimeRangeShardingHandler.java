package sharding.handler;

import com.google.common.collect.Range;
import org.apache.shardingsphere.core.strategy.route.value.RangeRouteValue;
import sharding.constant.ShardingConstant;
import sharding.constant.TimeRegexFormatter;

/**
 * 不明格式的时间字符分片Handler
 *
 * @author fun
 * @date 2019/7/15
 */
public class StringTimeRangeShardingHandler extends BaseRangeShardingHandler<String> {

    @Override
    public boolean canHandler(RangeRouteValue<String> routeValue) {
        Range range = routeValue.getValueRange();
        return range != null &&
                (!range.hasUpperBound() || range.upperEndpoint() instanceof String) &&
                (!range.hasLowerBound() || range.lowerEndpoint() instanceof String);
    }

    @Override
    protected String getActualTableNameByColumnValue(String logicTableName, String endpoint) {
        return logicTableName + "_" + ShardingConstant.FORMATTER.format(TimeRegexFormatter.matchAndParse(endpoint));
    }
}
