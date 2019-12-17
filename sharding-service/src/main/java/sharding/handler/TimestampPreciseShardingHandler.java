package sharding.handler;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.core.strategy.route.value.ListRouteValue;
import sharding.constant.ShardingConstant;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 基于创建时间的时间戳精确分片Handler，时间后缀 - yyMM
 *
 * @author fun
 * @date 2019/7/3
 */
public class TimestampPreciseShardingHandler extends BasePreciseShardingHandler<Timestamp> {

    @Override
    public boolean canHandler(ListRouteValue<Timestamp> routeValue) {
        Iterator iterator;
        return (iterator = routeValue.getValues().iterator()).hasNext() &&
                iterator.next() instanceof Timestamp;
    }

    @Override
    protected List<String> getActualTableNamesByColumnValue(ListRouteValue<Timestamp> listRouteValue) {
        Collection<Timestamp> values = listRouteValue.getValues();
        String logicTableName = listRouteValue.getTableName();
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        return values.stream()
                .filter(Objects::nonNull)
                .map(endpoint -> logicTableName + "_" + ShardingConstant.FORMATTER.format(endpoint.toLocalDateTime()))
                .collect(Collectors.toList());
    }
}
