package sharding.algorithm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.complex.ComplexKeysShardingValue;

import java.util.Collection;

/**
 * @author fun
 * @date 2019/6/26
 */
@Slf4j(topic = "sharding")
public class BaseComplexKeysShardingAlgorithm implements ComplexKeysShardingAlgorithm {

//    private LinkedList<String> columnList;
//
//    public BaseComplexKeysShardingAlgorithm(LinkedList<String> columnList) {
//        this.columnList = columnList;
//    }

    @Override
    public Collection<String> doSharding(Collection availableTargetNames, ComplexKeysShardingValue shardingValue) {
        log.info("availableTargetNames: {}\nshardingValue: {}", availableTargetNames, shardingValue);
        return availableTargetNames;
    }

}
