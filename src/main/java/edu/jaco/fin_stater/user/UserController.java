package edu.jaco.fin_stater.user;

import edu.jaco.fin_stater.transaction.TransactionRespository;
import jakarta.persistence.EntityManager;
import jakarta.servlet.http.Cookie;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.*;

@RestController
@RequestMapping("user")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionRespository transactionRespository;

    @Autowired
    private UserRoutingDataSource userRoutingDataSource;

    private final String transactionTableSql = "create table transaction (" +
        "amount float(53) not null," +
        "balance float(53) not null," +
        "used_for_calculation boolean not null," +
        "date timestamp(6)," +
        "id bigint not null," +
        "additional_info varchar(255)," +
        "additional_info_2 varchar(255)," +
        "category_match_keyword varchar(255)," +
        "description varchar(255)," +
        "receiver varchar(255)," +
        "sender varchar(255)," +
        "type varchar(255)," +
        "category enum (" +
            "'SPOZYWCZE'," +
            "'CIALO'," +
            "'TRANSPORT'," +
            "'OPLATY'," +
            "'DZIECI'," +
            "'DOM'," +
            "'ROWER'," +
            "'BANKOMAT'," +
            "'KARTA_KREDYTOWA'," +
            "'WAKACJE'," +
            "'OTHER')," +
        "frequency enum ('MONTHLY','OTHER','YEARLY'), " +
        "subcategory enum (" +
            "'SPOZYWCZE_ZAMAWIANE'," +
            "'SPOZYWCZE_SLODKOSCI'," +
            "'SPOZYWCZE_MARKETY'," +
            "'SPOZYWCZE_OTHER'," +
            "'CIALO_ZDROWIE'," +
            "'CIALO_HIGIENA'," +
            "'CIALO_BEAUTY'," +
            "'CIALO_OTHER'," +
            "'TRANSPORT_AUTO_PALIWO'," +
            "'TRANSPORT_AUTO_SERWIS'," +
            "'TRANSPORT_PARKING'," +
            "'TRANSPORT_BILETY'," +
            "'TRANSPORT_OTHER'," +
            "'DZIECI_PLACOWKI'," +
            "'DZIECI_OTHER'," +
            "'OPLATY_SUBSKRYPCJE'," +
            "'OPLATY_OTHER'," +
            "'OTHER'), " +
        "primary key (id))";

    private final String viewTableSql = "create table view (" +
            "avarage_balance float(53)," +
            "avarage_expenses float(53)," +
            "avarage_income float(53)," +
            "expenses float(53) not null," +
            "from_date date," +
            "income float(53) not null," +
            "period_balance float(53) not null," +
            "excluded float(53) not null," +
            "to_date date," +
            "id bigint not null," +
            "dtype varchar(31) not null," +
            "view_name varchar(255)," +
            "primary key (id))";

    private final String balanceMonthlyTableSql = "create table balance_monthly (" +
            "balance float(53) not null," +
            "expenses float(53) not null," +
            "income float(53) not null," +
            "rate_of_return float(53) not null," +
            "id bigint not null," +
            "month_name varchar(255)," +
            "primary key (id))";

    private final String categorizedTableSql = "create table categorized (" +
            "expense float(53) not null," +
            "id bigint not null," +
            "category enum (" +
                "'TRANSPORT'," +
                "'BANKOMAT'," +
                "'DOM'," +
                "'DZIECI'," +
                "'CIALO'," +
                "'KARTA_KREDYTOWA'," +
                "'OPLATY'," +
                "'OTHER'," +
                "'ROWER'," +
                "'SPOZYWCZE'," +
                "'CHARYTATYWNE'," +
                "'VINTED'," +
                "'REVOLUT'," +
                "'KONTO_WSPOLNE'," +
                "'PORCELANA'," +
                "'ONLINE'), " +
            "primary key (id))";

    private final String categorizedMonthlyTableSql = "create table categorized_monthly (" +
        "expense float(53) not null," +
        "categorized_monthly_id bigint," +
        "id bigint not null," +
        "category enum (" +
            "'TRANSPORT'," +
            "'BANKOMAT'," +
            "'DOM'," +
            "'DZIECI'," +
            "'CIALO'," +
            "'KARTA_KREDYTOWA'," +
            "'OPLATY'," +
            "'OTHER'," +
            "'ROWER'," +
            "'SPOZYWCZE'," +
            "'CHARYTATYWNE'," +
            "'VINTED'," +
            "'REVOLUT'," +
            "'KONTO_WSPOLNE'," +
            "'PORCELANA'," +
            "'ONLINE'), " +
        "primary key (id))";

    private final String categorizedSubcategoryTableSql = "create table categorized_subcategory_stat (" +
            "subcategory_stat float(53), " +
            "subcategory_stat_key tinyint not null check (subcategory_stat_key between 0 and 17), " +
            "categorized_id bigint not null, " +
            "primary key (subcategory_stat_key, categorized_id))";

    @Value("${DB_URL}")
    private String dbUrl;

    @CrossOrigin
    @PostMapping("create/{name}/{password}")
    public void createUser(@RequestHeader("mode") String mode, @PathVariable String name, @PathVariable String password) {
        logger.info("createUser - entered");

        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection connection) throws SQLException {
                Statement statement = connection.createStatement();
                statement.execute("create user " + name + " password '" + password + "'");
                String schemaName = "fin_stater_" + name + "_schema";
                statement.execute("create schema " + schemaName + " authorization " + name);
                statement.execute("set schema " + schemaName);
                ResultSet currentSchema = statement.executeQuery("call current_schema");
                while(currentSchema.next()) {
                    System.out.println(currentSchema.getString(1));
                }
                statement.execute(transactionTableSql);
                statement.execute("create sequence transaction_seq start with 1 increment by 50");

                statement.execute(viewTableSql);
                statement.execute("create sequence view_seq start with 1 increment by 50");

                statement.execute(balanceMonthlyTableSql);
                statement.execute("create sequence balance_monthly_seq start with 1 increment by 50");

                statement.execute(categorizedTableSql);
                statement.execute("create sequence categorized_seq start with 1 increment by 50");
                statement.execute(categorizedSubcategoryTableSql);

                statement.execute(categorizedMonthlyTableSql);
                statement.execute("create sequence categorized_monthly_seq start with 1 increment by 50");
            }
        });

        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
        dataSourceBuilder.url(dbUrl + ";SCHEMA=FIN_STATER_" + name + "_SCHEMA");
        dataSourceBuilder.username(name);
        dataSourceBuilder.password(password);
        DataSource newDs = dataSourceBuilder.build();

        Map<Object, DataSource> currentDataSources = userRoutingDataSource.getResolvedDataSources();
        Map<Object, Object> dsCopy = new HashMap<>(currentDataSources);
        dsCopy.put(name.toUpperCase(), newDs);
        userRoutingDataSource.setTargetDataSources(dsCopy);
        userRoutingDataSource.setLookupKey(name.toUpperCase());

        logger.info("createUser - exiting");
    }

    @CrossOrigin
    @PostMapping("login")
    public Cookie loginUser(@RequestHeader("mode") String mode, @RequestBody String creds) {
        logger.info("loginUser - entered");

        String decodedCreds = new String(Base64.getDecoder().decode(creds));
        String lookUpKey = "FIN_STATER_" + decodedCreds.split(":")[0].toUpperCase() + "_SCHEMA";
        userRoutingDataSource.setLookupKey(lookUpKey);

        logger.info("loginUser - exiting");

        return new Cookie("session-id", UUID.randomUUID().toString());
    }
}
