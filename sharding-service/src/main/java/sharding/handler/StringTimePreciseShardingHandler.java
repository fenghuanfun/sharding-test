package sharding.handler;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.core.strategy.route.value.ListRouteValue;
import sharding.constant.ShardingConstant;
import sharding.constant.TimeRegexFormatter;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 不明格式的时间字符分片Handler
 *
 * @author fun
 * @date 2019/7/15
 */
public class StringTimePreciseShardingHandler extends BasePreciseShardingHandler<String> {

    @Override
    public boolean canHandler(ListRouteValue<String> routeValue) {
        Iterator iterator;
        return (iterator = routeValue.getValues().iterator()).hasNext() &&
                iterator.next() instanceof String;
    }

    @Override
    protected List<String> getActualTableNamesByColumnValue(ListRouteValue<String> listRouteValue) {
        Collection<String> values = listRouteValue.getValues();
        String logicTableName = listRouteValue.getTableName();
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        return values.stream()
                .filter(Objects::nonNull)
                .map(endpoint -> logicTableName + "_" + ShardingConstant.FORMATTER.format(TimeRegexFormatter.matchAndParse(endpoint)))
                .collect(Collectors.toList());
    }

}
