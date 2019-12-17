package sharding.handler;

import com.google.common.collect.Range;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.core.strategy.route.value.RangeRouteValue;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 范围的分片Handler
 *
 * @author fun
 * @date 2019/7/3
 */
public abstract class BaseRangeShardingHandler<T extends Comparable<?>> implements ShardingHandler<RangeRouteValue<T>> {

    @Override
    public Collection<String> handleRoutedTableNames(Collection<String> availableTargetNames, RangeRouteValue<T> rangeRouteValue) {
        Range<T> range = rangeRouteValue.getValueRange();
        String logicTableName = rangeRouteValue.getTableName();
        Range<String> tableNameRange;
        if (range.hasLowerBound() && range.hasUpperBound()) {
            tableNameRange = Range.range(
                    getActualTableNameByColumnValue(logicTableName, range.lowerEndpoint()),
                    range.lowerBoundType(),
                    getActualTableNameByColumnValue(logicTableName, range.upperEndpoint()),
                    range.upperBoundType());
        } else if (range.hasLowerBound()) {
            tableNameRange = Range.downTo(
                    getActualTableNameByColumnValue(logicTableName, range.lowerEndpoint()),
                    range.lowerBoundType());
        } else if (range.hasUpperBound()) {
            tableNameRange = Range.upTo(
                    getActualTableNameByColumnValue(logicTableName, range.upperEndpoint()),
                    range.upperBoundType());
        } else {
            return availableTargetNames;
        }
        List<String> collect = availableTargetNames.stream()
                .filter(tableNameRange::contains)
                .collect(Collectors.toList());
        return CollectionUtils.isEmpty(collect) ? availableTargetNames : collect;
    }

    /**
     * 根据查询值获得实际表名
     *
     * @param logicTableName
     * @param endpoint
     * @return
     */
    protected abstract String getActualTableNameByColumnValue(String logicTableName, T endpoint);
}
