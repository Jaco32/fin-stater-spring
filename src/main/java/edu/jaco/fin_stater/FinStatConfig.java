package edu.jaco.fin_stater;

import edu.jaco.fin_stater.user.UserRoutingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class FinStatConfig {

    @Value("${DB_URL}")
    private String dbUrl;

    @Value("${DB_ADMIN}")
    private String user;

    @Value("${DB_ADMIN_PASSWORD}")
    private String password;

    @Value("${DB_DEFAULT_SCHEMA}")
    private String defaultSchema;

    @Bean
    public DataSource routingDataSource() {
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

    @Bean
    public Integer initRoutingDs(LocalContainerEntityManagerFactoryBean entityManagerFactory,
                                 UserRoutingDataSource routingDataSource)
            throws SQLException
    {
        DataSource ds = entityManagerFactory.getDataSource();
        Connection connection = ds.getConnection();
        Statement statement = connection.createStatement();
        ResultSet schemas = statement.executeQuery("show schemas");
        Map<Object, DataSource> currentDataSources = routingDataSource.getResolvedDataSources();
        Map<Object, Object> dsCopy = new HashMap<>(currentDataSources);
        while(schemas.next()) {
            String schema = schemas.getString(1);
            if (schema.startsWith("FIN_STATER")) {
                DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
                dataSourceBuilder.url(dbUrl + ";SCHEMA=" + schema);
                dataSourceBuilder.username(user);
                dataSourceBuilder.password(password);
                DataSource newDs = dataSourceBuilder.build();
                System.out.println("Adding " + dbUrl + ";SCHEMA=" + schema);
                dsCopy.put(schema, newDs);
            }
        }
        routingDataSource.setTargetDataSources(dsCopy);
        routingDataSource.afterPropertiesSet();
        return Integer.valueOf(20);
    }
}
