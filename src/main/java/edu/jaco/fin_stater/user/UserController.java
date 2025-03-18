package edu.jaco.fin_stater.user;

import edu.jaco.fin_stater.transaction.TransactionRespository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private TransactionRespository transactionRespository;

    @Autowired
    private DataSource dataSource;

    @CrossOrigin
    @PostMapping("create/{name}")
    public void createUser(@RequestHeader("mode") String mode, @PathVariable String name) {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection hibernateConnection) throws SQLException {
                Connection connection = hibernateConnection;
                Statement statement = connection.createStatement();
                String schemaName = "fin_stater_" + name + "_schema";
                statement.execute("create schema " + schemaName);
                statement.execute("set schema " + schemaName);
                ResultSet currentSchema = statement.executeQuery("call current_schema");
                while(currentSchema.next()) {
                    System.out.println(currentSchema.getString(1));
                }
                statement.execute("create table transaction (amount float(53) not null, balance float(53) not null, used_for_calculation boolean not null, date timestamp(6), id bigint not null, additional_info varchar(255), additional_info_2 varchar(255), category_match_keyword varchar(255), description varchar(255), receiver varchar(255), sender varchar(255), type varchar(255), category enum ('AUTO','BANKOMAT','DOM','DOMINO','DZIECI','HIGIENA','KARTA_KREDYTOWA','OPLATY','OTHER','PALIWO','ROWER','SPOZYWCZE','ZDROWIE','CHARYTATYWNE','VINTED','REVOLUT','KONTO_WSPOLNE','PORCELANA','ONLINE'), frequency enum ('MONTHLY','OTHER','YEARLY'), subcategory enum ('OTHER','SPOZYWCZE_ZAMAWIANE'), primary key (id))");
                statement.execute("create sequence transaction_seq start with 1 increment by 50");

                statement.execute("create table balance (expenses float(53) not null, from_date date, income float(53) not null, period_balance float(53) not null, to_date date, id bigint not null, view_name varchar(255), primary key (id))");
                statement.execute("create sequence balance_seq start with 1 increment by 50");

                statement.execute("create table balance_monthly (balance float(53) not null, expenses float(53) not null, income float(53) not null, rate_of_return float(53) not null, id bigint not null, month_name varchar(255), primary key (id))");
                statement.execute("create sequence balance_monthly_seq start with 1 increment by 50");

                statement.execute("create table categorized (expense float(53) not null, id bigint not null, category enum ('AUTO','BANKOMAT','DOM','DOMINO','DZIECI','HIGIENA','KARTA_KREDYTOWA','OPLATY','OTHER','PALIWO','ROWER','SPOZYWCZE','ZDROWIE','CHARYTATYWNE','VINTED','REVOLUT','KONTO_WSPOLNE','PORCELANA','ONLINE'), primary key (id))");
                statement.execute("create sequence categorized_seq start with 1 increment by 50");

                statement.execute("create table balance_avarage (avarage_balance float(53) not null, avarage_expenses float(53) not null, avarage_income float(53) not null, id bigint not null, primary key (id))");
                statement.execute("create sequence balance_avarage_seq start with 1 increment by 50");

                statement.execute("create table categorized_monthly (expense float(53) not null, categorized_monthly_id bigint, id bigint not null, category enum ('AUTO','BANKOMAT','DOM','DOMINO','DZIECI','HIGIENA','KARTA_KREDYTOWA','OPLATY','OTHER','PALIWO','ROWER','SPOZYWCZE','ZDROWIE','CHARYTATYWNE','VINTED','REVOLUT','KONTO_WSPOLNE','PORCELANA','ONLINE'), primary key (id))");
                statement.execute("create sequence categorized_monthly_seq start with 1 increment by 50");
            }
        });
    }
}
