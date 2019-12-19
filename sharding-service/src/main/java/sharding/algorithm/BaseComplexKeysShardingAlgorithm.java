package sharding.algorithm;

import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;
import org.apache.shardingsphere.core.strategy.route.value.ListRouteValue;
import org.apache.shardingsphere.core.strategy.route.value.RangeRouteValue;
import org.springframework.util.Assert;
import sharding.factory.ComplexKeyShadingHandlerBuilder;
import sharding.handler.BasePreciseShardingHandler;
import sharding.handler.BaseRangeShardingHandler;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fun
 * @date 2019/6/26
 */
@Slf4j(topic = "Sharding")
public class BaseComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    private final LinkedHashMap<String, List<BasePreciseShardingHandler<? extends Comparable<?>>>> preciseHandlers;
    private final LinkedHashMap<String, List<BaseRangeShardingHandler<? extends Comparable<?>>>> rangeHandlers;

    public BaseComplexKeysShardingAlgorithm(ComplexKeyShadingHandlerBuilder builder) {
        Assert.notNull(builder, "ShardingHandlers can not be null");
        if (MapUtils.isEmpty(builder.getPreciseHandlers()) && MapUtils.isEmpty(builder.getRangeHandlers())) {
            throw new IllegalArgumentException("ShardingHandlers can not be empty");
        }
        this.preciseHandlers = builder.getPreciseHandlers();
        this.rangeHandlers = builder.getRangeHandlers();
    }

    /**
     * @param availableTargetNames
     * @param shardingValue
     * @return
     */
    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
        if (log.isDebugEnabled()) {
            log.debug("availableTargetNames: {}\nshardingValue: {}", availableTargetNames, shardingValue);
        }
        Map<String, Collection<String>> preciseMap;
        Map<String, Range<String>> rangeMap;
        if (MapUtils.isNotEmpty(preciseMap = shardingValue.getColumnNameAndShardingValuesMap())) {
            for (Map.Entry<String, Collection<String>> entry : preciseMap.entrySet()) {
                List<BasePreciseShardingHandler<? extends Comparable<?>>> handlers = preciseHandlers.get(entry.getKey());
                if (CollectionUtils.isNotEmpty(handlers)) {
                    Collection<String> actualTableNames = handleList(
                            availableTargetNames,
                            handlers,
                            getListRouteValue(shardingValue.getLogicTableName(), entry.getKey(), entry.getValue()));
                    if (CollectionUtils.isEmpty(actualTableNames)) {
                        log.error("Can not route to a right table:\n{}", shardingValue);
                        throw new RuntimeException();
                    }
                    return actualTableNames;
                }
            }
        } else if (MapUtils.isNotEmpty(rangeMap = shardingValue.getColumnNameAndRangeValuesMap())) {
            for (Map.Entry<String, Range<String>> entry : rangeMap.entrySet()) {
                List<BaseRangeShardingHandler<? extends Comparable<?>>> handlers = rangeHandlers.get(entry.getKey());
                if (CollectionUtils.isNotEmpty(handlers)) {
                    Collection<String> actualTableNames = handleRange(
                            availableTargetNames,
                            handlers,
                            getRangeRouteValue(shardingValue.getLogicTableName(), entry.getKey(), entry.getValue()));
                    if (CollectionUtils.isEmpty(actualTableNames)) {
                        log.error("Can not route to a right table:\n{}", shardingValue);
                        throw new RuntimeException();
                    }
                    return actualTableNames;
                }
            }
        }
        log.warn("No available tables, return all -> {}", shardingValue);
        return availableTargetNames;
    }

    private static Collection<String> handleList(Collection<String> availableTargetNames, List<BasePreciseShardingHandler<? extends Comparable<?>>> handlers, ListRouteValue listRouteValue) {
        for (BasePreciseShardingHandler<? extends Comparable<?>> handler : handlers) {
            //noinspection unchecked
            if (handler.canHandler(listRouteValue)) {
                //noinspection unchecked
                return handler.handleRoutedTableNames(availableTargetNames, listRouteValue);
            }
        }
        return null;
    }

    private static Collection<String> handleRange(Collection<String> availableTargetNames, List<BaseRangeShardingHandler<? extends Comparable<?>>> handlers, RangeRouteValue rangeRouteValue) {
        for (BaseRangeShardingHandler<? extends Comparable<?>> handler : handlers) {
            //noinspection unchecked
            if (handler.canHandler(rangeRouteValue)) {
                //noinspection unchecked
                return handler.handleRoutedTableNames(availableTargetNames, rangeRouteValue);
            }
        }
        return null;
    }

    private static ListRouteValue<? extends Comparable<?>> getListRouteValue(String logicTableName, String columnName, Collection<? extends Comparable<?>> values) {
        return new ListRouteValue<>(columnName, logicTableName, values);
    }

    private static RangeRouteValue<? extends Comparable<?>> getRangeRouteValue(String logicTableName, String columnName, Range<? extends Comparable<?>> range) {
        return new RangeRouteValue<>(columnName, logicTableName, range);
    }
}
