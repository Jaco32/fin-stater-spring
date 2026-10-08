package edu.jaco.fin_stater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.securityMatcher("/transaction/upload/", "/transaction/add/", "/transaction/toogleforstats/*",
                        "/stat", "/stat/*", "/stat/view/*", "/stat/view/switch/*", "/stat/clear")
                .csrf().disable()
                .cors(cors -> cors.configure(httpSecurity))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.PATCH, "/transaction/toogleforstats/*").permitAll())
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(HttpMethod.GET, "/stat", "/stat/*").permitAll()
                )
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(HttpMethod.PATCH, "/stat/view/*", "/stat/clear").permitAll()
                )
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/stat/view/switch/*").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/transaction/upload/", "/transaction/add/").hasAnyRole("USER").anyRequest().authenticated());

        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsManager jdbcUserDetailsManager(DataSource dataSource, PasswordEncoder passwordEncoder)
    {
        return new JdbcUserDetailsManager(dataSource);
    }
}
