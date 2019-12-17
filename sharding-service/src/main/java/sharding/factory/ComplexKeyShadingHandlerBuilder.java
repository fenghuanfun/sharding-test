package sharding.factory;


import lombok.Getter;
import org.apache.shardingsphere.core.strategy.route.value.RouteValue;
import sharding.handler.BasePreciseShardingHandler;
import sharding.handler.BaseRangeShardingHandler;
import sharding.handler.ShardingHandler;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * @author fun
 * @date 2019/7/3
 */
public class ComplexKeyShadingHandlerBuilder {

    @Getter
    private LinkedHashMap<String, List<BasePreciseShardingHandler<? extends Comparable<?>>>> preciseHandlers;
    @Getter
    private LinkedHashMap<String, List<BaseRangeShardingHandler<? extends Comparable<?>>>> rangeHandlers;

    private ComplexKeyShadingHandlerBuilder() {
        preciseHandlers = new LinkedHashMap<>();
        rangeHandlers = new LinkedHashMap<>();
    }

    public static ComplexKeyShadingHandlerBuilder builder() {
        return new ComplexKeyShadingHandlerBuilder();
    }

    /**
     * 注意添加顺序
     *
     * @param columnName
     * @param shardingHandler
     * @return
     */
    public ComplexKeyShadingHandlerBuilder addHandler(String columnName, ShardingHandler<? extends RouteValue> shardingHandler) {
        if (shardingHandler instanceof BasePreciseShardingHandler) {
            List<BasePreciseShardingHandler<? extends Comparable<?>>> handlers = preciseHandlers.getOrDefault(columnName, new LinkedList<>());
            handlers.add((BasePreciseShardingHandler<? extends Comparable<?>>) shardingHandler);
            preciseHandlers.put(columnName, handlers);
        } else if (shardingHandler instanceof BaseRangeShardingHandler) {
            List<BaseRangeShardingHandler<? extends Comparable<?>>> handlers = rangeHandlers.getOrDefault(columnName, new LinkedList<>());
            handlers.add((BaseRangeShardingHandler<? extends Comparable>) shardingHandler);
            rangeHandlers.put(columnName, handlers);
        }
        return this;
    }

    public ComplexKeyShadingHandlerBuilder build() {
        return this;
    }

}
