package sharding.algorithm;

import org.springframework.lang.NonNull;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

/**
 * @author fun
 * @date 2019/6/13
 */
public class TimeStampPreciseShardingAlgorithm extends BasePreciseShardingAlgorithm<Timestamp> {

    static final String DEFAULT_DATE_PATTERN = "yyMM";

    private DateTimeFormatter formatter;

    public TimeStampPreciseShardingAlgorithm() {
        this(DEFAULT_DATE_PATTERN);
    }

    public TimeStampPreciseShardingAlgorithm(String pattern) {
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    @Override
    protected String getTableSuffix(@NonNull Timestamp value) {
        return value.toLocalDateTime().format(formatter);
    }
}
