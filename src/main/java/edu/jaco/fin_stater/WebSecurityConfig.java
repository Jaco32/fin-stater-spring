package edu.jaco.fin_stater;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .cors(cors -> cors.configure(httpSecurity))
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/user/create/*/*").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/user/login/*/*").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.POST, "/transaction/upload/").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.PATCH, "/transaction/*").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/transaction").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/stat").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/stat/*").permitAll())
                .authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.PATCH, "/stat/view/*").permitAll());
        return httpSecurity.build();
    }

}
