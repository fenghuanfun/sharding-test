package sharding.algorithm;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;

/**
 * @author fun
 * @date 2019/6/26
 */
@Slf4j(topic = "sharding")
public class StringTimestampComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm<String> {

    @Override
    public Collection<String> doSharding(Collection<String> availableTargetNames, ComplexKeysShardingValue<String> shardingValue) {
        log.info(JSONObject.toJSONString(shardingValue));
        Range<?> range = shardingValue.getColumnNameAndRangeValuesMap().get("create_time");
        Collection<?> collection = shardingValue.getColumnNameAndShardingValuesMap().get("create_time");
        System.out.println(range);
        System.out.println(collection);
        return availableTargetNames;
    }

}
