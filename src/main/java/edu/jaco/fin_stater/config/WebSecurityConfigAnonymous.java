package edu.jaco.fin_stater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigAnonymous {

    @Bean
    public SecurityFilterChain securityFilterChainAnonymous(HttpSecurity httpSecurityBuilder) throws Exception {
        httpSecurityBuilder.securityMatcher("/user/create")
                .csrf().disable()
                .cors(cors -> cors.configure(httpSecurityBuilder))
                .authorizeHttpRequests(authorize ->
                        authorize.requestMatchers(HttpMethod.POST, "/user/create").permitAll()
                );

        return httpSecurityBuilder.build();
    }
}
