package com.example.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests((requests) -> {
                    requests.antMatchers("/", "/registration","/products").permitAll();
                    requests.antMatchers("/admin","/admin/*").hasAnyRole("ADMIN","MODERATOR");
                    requests.anyRequest().authenticated();
                })
                .formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/products")
                        .permitAll()
                )
                .logout(LogoutConfigurer::permitAll);

        return httpSecurity.build();
    }
}
