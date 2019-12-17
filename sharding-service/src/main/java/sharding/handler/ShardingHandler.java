package sharding.handler;


import org.apache.shardingsphere.core.strategy.route.value.RouteValue;

import java.util.Collection;

/**
 * @author fun
 * @date 2019/7/3
 */
public interface ShardingHandler<R extends RouteValue> {

    /**
     * 获取路由表名称
     *
     * @param availableTargetNames
     * @param routeValue
     * @return
     */
    Collection<String> handleRoutedTableNames(Collection<String> availableTargetNames, R routeValue);

    /**
     * 是否可以处理
     *
     * @param routeValue
     * @return
     */
    boolean canHandler(R routeValue);

}
