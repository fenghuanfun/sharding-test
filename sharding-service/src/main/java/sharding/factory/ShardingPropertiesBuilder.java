package sharding.factory;

import org.apache.shardingsphere.core.constant.properties.ShardingPropertiesConstant;

import java.util.Properties;

/**
 * @author fun
 * @date 2019/6/14
 */
public class ShardingPropertiesBuilder {

    private Properties properties = new Properties();

    public ShardingPropertiesBuilder showSql(boolean showSql) {
        properties.setProperty(ShardingPropertiesConstant.SQL_SHOW.getKey(), String.valueOf(showSql));
        return this;
    }

    public ShardingPropertiesBuilder simpleSql(boolean simpleSql) {
        properties.setProperty(ShardingPropertiesConstant.SQL_SIMPLE.getKey(), String.valueOf(simpleSql));
        return this;
    }

    public Properties build() {
        return properties;
    }

}
