package sharding.algorithm;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * @author fun
 * @date 2019/6/19
 */
@Slf4j(topic = "sharding")
public abstract class BaseRangeShardingAlgorithm<C extends Comparable<?>> implements RangeShardingAlgorithm<C> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, RangeShardingValue<C> shardingValue) {
        if (log.isDebugEnabled()) {
            log.debug("availableTargetNames: {}\nshardingValue: {}", availableTargetNames, shardingValue);
        }
        log.info("availableTargetNames: {}\nshardingValue: {}", availableTargetNames, shardingValue);
        Range<String> range = this.convertWithTableName(shardingValue, this::getActualTableName);
        if (range == null) {
            return availableTargetNames;
        }
        List<String> list = availableTargetNames.stream()
                .filter(range::contains)
                .collect(Collectors.toList());
        log.info(JSONObject.toJSONString(list));
        return CollectionUtils.isEmpty(list) ? availableTargetNames : list;
    }

    /**
     * 真实的表名的拼接
     *
     * @param endpoint
     * @param logicTableName
     * @return
     */
    public abstract String getActualTableName(C endpoint, String logicTableName);

    /**
     * 将范围型的分片Range 转换为带表名的Range
     *
     * @param shardingValue
     * @param tableNameFunction
     * @return
     */
    private Range<String> convertWithTableName(RangeShardingValue<C> shardingValue,
                                               BiFunction<C, String, String> tableNameFunction) {
        Range<C> range = shardingValue.getValueRange();
        String logicTableName = shardingValue.getLogicTableName();
        if (range.hasLowerBound() && range.hasUpperBound()) {
            return Range.range(
                    tableNameFunction.apply(range.lowerEndpoint(), logicTableName),
                    range.lowerBoundType(),
                    tableNameFunction.apply(range.upperEndpoint(), logicTableName),
                    range.upperBoundType());
        } else if (range.hasLowerBound()) {
            return Range.downTo(
                    tableNameFunction.apply(range.lowerEndpoint(), logicTableName),
                    range.lowerBoundType());
        } else if (range.hasUpperBound()) {
            return Range.upTo(
                    tableNameFunction.apply(range.upperEndpoint(), logicTableName),
                    range.upperBoundType());
        }
        return null;
    }
}
