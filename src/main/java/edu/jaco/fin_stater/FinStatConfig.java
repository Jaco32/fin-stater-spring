package edu.jaco.fin_stater;

import edu.jaco.fin_stater.user.UserRoutingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FinStatConfig {

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_ADMIN}")
    private String user;

    @Value("{DB_ADMIN_PASSWORD}")
    private String password;

    @Value("{DB_DEFAULT_SCHEMA}")
    private String defaultSchema;

    @Bean
    public DataSource getRoutingDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<>();

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();

        dataSourceBuilder.url(dbUrl + ";SCHEMA=" + defaultSchema);
        dataSourceBuilder.username(user);
        dataSourceBuilder.password(password);
        DataSource dsDefault = dataSourceBuilder.build();
        targetDataSources.put(defaultSchema, dsDefault);

        UserRoutingDataSource userRoutingDataSource = new UserRoutingDataSource();
        userRoutingDataSource.setTargetDataSources(targetDataSources);
        userRoutingDataSource.setDefaultTargetDataSource(dsDefault);

        return userRoutingDataSource;
    }
}
