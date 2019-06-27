package sharding.algorithm;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

/**
 * @author fun
 * @date 2019/6/13
 */
public class TimeStampRangeShardingAlgorithm extends BaseRangeShardingAlgorithm<Timestamp> {

    private DateTimeFormatter formatter;

    public TimeStampRangeShardingAlgorithm() {
        this(TimeStampPreciseShardingAlgorithm.DEFAULT_DATE_PATTERN);
    }

    public TimeStampRangeShardingAlgorithm(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    public String getActualTableName(Timestamp endpoint, String logicTableName) {
        return String.format("%s_%s", logicTableName, formatter.format(endpoint.toLocalDateTime()));
    }
}
