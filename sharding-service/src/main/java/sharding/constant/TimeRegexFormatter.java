package sharding.constant;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

/**
 * @author fun
 * @date 2019/7/15
 */
public enum TimeRegexFormatter {
    //
    HH("^\\d{2}$", "HH"),
    Hm("^\\d{2}:\\d{2}$", "HH:mm"),
    Hms("^\\d{2}:\\d{2}:\\d{2}$", "HH:mm:ss"),
    MdHm("^\\d{2}-\\d{2} \\d{2}:\\d{2}$", "MM-dd HH:mm"),
    yMd("^\\d{4}-\\d{2}-\\d{2}$", "yyyy-MM-dd"),
    yMdH00("^\\d{4}-\\d{2}-\\d{2} \\d{2}$", "yyyy-MM-dd HH:00:00"),
    yMdHm0("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:00"),
    yMdHms("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$", "yyyy-MM-dd HH:mm:ss");

    private String regex;

    @Getter
    private DateTimeFormatter formatter;

    @Getter
    private String pattern;

    TimeRegexFormatter(String regex, String pattern) {
        this.regex = regex;
        this.pattern = pattern;
        this.formatter = DateTimeFormatter.ofPattern(pattern);
    }

    public static TemporalAccessor matchAndParse(String time) {
        if (StringUtils.isNotEmpty(time)) {
            for (TimeRegexFormatter value : TimeRegexFormatter.values()) {
                if (time.matches(value.regex)) {
                    return value.formatter.parse(time);
                }
            }
        }
        throw new RuntimeException();
    }

}