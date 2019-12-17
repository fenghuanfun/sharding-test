package sharding.handler;

import org.apache.commons.collections4.ListUtils;
import org.apache.shardingsphere.core.strategy.route.value.ListRouteValue;

import java.util.Collection;
import java.util.List;

/**
 * 精确的分片Handler
 *
 * @author fun
 * @date 2019/7/3
 */
public abstract class BasePreciseShardingHandler<T extends Comparable<?>> implements ShardingHandler<ListRouteValue<T>> {

    @Override
    public Collection<String> handleRoutedTableNames(Collection<String> availableTargetNames, ListRouteValue<T> listRouteValue) {
        List<String> tableNames = getActualTableNamesByColumnValue(listRouteValue);
        return tableNames == null ? availableTargetNames : ListUtils.retainAll(availableTargetNames, tableNames);
    }

    /**
     * 根据查询值返回相应的表名称
     *
     * @param listRouteValue
     * @return
     */
    protected abstract List<String> getActualTableNamesByColumnValue(ListRouteValue<T> listRouteValue);

}
