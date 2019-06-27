package sharding.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import sharding.IShardingConfiguration;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author fun
 * @date 2019/6/12
 */
@SpringBootConfiguration
public class ShardingDataSourceConfig {

    @Resource
    private IShardingConfiguration shardingConfiguration;

    @Resource
    private DataSourceConfig dataSourceConfig;

    @Bean
    public DataSource dataSource() throws SQLException {
        return ShardingDataSourceFactory.createDataSource(
                createDataSourceMap(),
                shardingConfiguration.getShardingRuleConfiguration(),
                shardingConfiguration.getProperties());
    }

    private Map<String, DataSource> createDataSourceMap() {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setUrl(dataSourceConfig.getUrl());
        druidDataSource.setPassword(dataSourceConfig.getPassword());
        druidDataSource.setUsername(dataSourceConfig.getUsername());
        druidDataSource.setDriverClassName(dataSourceConfig.getDriverClassName());
        return Map.of(dataSourceConfig.getDatasourceName(), druidDataSource);
    }

}
