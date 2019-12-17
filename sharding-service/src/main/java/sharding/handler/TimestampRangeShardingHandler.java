package sharding.handler;

import com.google.common.collect.Range;
import org.apache.shardingsphere.core.strategy.route.value.RangeRouteValue;
import sharding.constant.ShardingConstant;

import java.sql.Timestamp;

/**
 * 基于创建时间的时间戳范围分片Handler，时间后缀 - yyMM
 *
 * @author fun
 * @date 2019/7/3
 */
public class TimestampRangeShardingHandler extends BaseRangeShardingHandler<Timestamp> {

    @Override
    public boolean canHandler(RangeRouteValue<Timestamp> routeValue) {
        Range range = routeValue.getValueRange();
        return range != null &&
                (!range.hasUpperBound() || range.upperEndpoint() instanceof Timestamp) &&
                (!range.hasLowerBound() || range.lowerEndpoint() instanceof Timestamp);
    }

    @Override
    protected String getActualTableNameByColumnValue(String logicTableName, Timestamp endpoint) {
        return logicTableName + "_" + ShardingConstant.FORMATTER.format(endpoint.toLocalDateTime());
    }
}
