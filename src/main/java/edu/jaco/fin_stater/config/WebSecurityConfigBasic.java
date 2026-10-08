package edu.jaco.fin_stater.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigBasic {

    @Bean
    public SecurityFilterChain securityFilterChainBasic(HttpSecurity httpSecurityBuilder) throws Exception {
        httpSecurityBuilder.securityMatcher("/user/login", "/transaction")
                .csrf().disable()
                .cors(cors -> cors.configure(httpSecurityBuilder))
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults());

        return httpSecurityBuilder.build();
    }
}
