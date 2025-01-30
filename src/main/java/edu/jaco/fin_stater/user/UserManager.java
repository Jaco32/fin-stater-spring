package edu.jaco.fin_stater.user;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class UserManager {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private DataSource dataSource;

    private Statement statement;
    private Connection connection;

    public void signUpUser() throws SQLException {
        Session session = entityManager.unwrap(Session.class);
        session.doWork(new Work() {
            @Override
            public void execute(Connection hibernateConnection) throws SQLException {
                connection = hibernateConnection;
                statement = connection.createStatement();
                statement.execute("create schema fin_stater_example_user_schema");
                statement.execute("set schema fin_stater_example_user_schema");
                ResultSet currentSchema = statement.executeQuery("call current_schema");
                while(currentSchema.next()) {
                    System.out.println(currentSchema.getString(1));
                }
                statement.execute("create table transaction (amount float(53) not null, balance float(53) not null, date timestamp(6), id bigint not null, additional_info varchar(255), additional_info_2 varchar(255), category_match_keyword varchar(255), description varchar(255), receiver varchar(255), sender varchar(255), type varchar(255), category enum ('AUTO','BANKOMAT','DOM','DOMINO','DZIECI','HIGIENA','KARTA_KREDYTOWA','OPLATY','OTHER','PALIWO','ROWER','SPOZYWCZE','ZDROWIE'), frequency enum ('MONTHLY','OTHER','YEARLY'), subcategory enum ('OTHER','SPOZYWCZE_ZAMAWIANE'), primary key (id))");
                statement.execute("create sequence transaction_seq start with 1 increment by 50");
                System.out.println("seq created");
            }
        });
    }

    public void checkUserTransactionTableSize() throws SQLException {
        connection = dataSource.getConnection();
        statement = connection.createStatement();
        ResultSet size = statement.executeQuery("select count(*) from fin_stater_example_user_schema.transaction");
        while(size.next()) {
            System.out.println(size.getString(1));
        }
        ResultSet tables = statement.executeQuery("show tables from public");
        while(tables.next()) {
            System.out.println(tables.getString(1));
        }
        ResultSet currentSchema = statement.executeQuery("call current_schema");
        while(currentSchema.next()) {
            System.out.println(currentSchema.getString(1));
        }
    }
}
