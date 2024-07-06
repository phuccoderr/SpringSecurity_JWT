package com.phuc.sociallogin.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class WebSecurity {

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz.requestMatchers("/")
                .permitAll()
                .anyRequest().authenticated()
                )
                .oauth2Login( o -> {});
    }
}
