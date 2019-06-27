package sharding.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;
import org.springframework.lang.NonNull;

import java.util.Collection;

/**
 * @author fun
 * @date 2019/6/19
 */
@Slf4j(topic = "sharding")
public abstract class BasePreciseShardingAlgorithm<C extends Comparable<?>> implements PreciseShardingAlgorithm<C> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<C> shardingValue) {
        if (log.isDebugEnabled()) {
            log.debug("availableTargetNames: {}\nshardingValue: {}", availableTargetNames, shardingValue);
        }
        return availableTargetNames.stream()
                .filter(tableName -> StringUtils.endsWith(tableName, getTableSuffix(shardingValue.getValue())))
                .findFirst()
                .orElseThrow();
    }

    /**
     * 真实的表名将以该方法的返回值结束
     *
     * @param value
     * @return
     */
    protected abstract String getTableSuffix(@NonNull C value);

}
