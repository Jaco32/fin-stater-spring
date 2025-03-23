package edu.jaco.fin_stater;

import edu.jaco.fin_stater.user.UserRoutingDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FinStatConfig {

    @Bean
    public DataSource getRoutingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.url("jdbc:h2:~/finstat;SCHEMA=PUBLIC");
        DataSource dsDefault = dataSourceBuilder.build();
        targetDataSources.put("PUBLIC", dsDefault);

        UserRoutingDataSource userRoutingDataSource = new UserRoutingDataSource();
        userRoutingDataSource.setTargetDataSources(targetDataSources);
        userRoutingDataSource.setDefaultTargetDataSource(dsDefault);

        return userRoutingDataSource;
    }
}
