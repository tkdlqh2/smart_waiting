package com.example.smart_waiting.config;

import com.example.smart_waiting.security.JwtAuthenticationFilter;
import com.example.smart_waiting.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfiguration{

    private final JwtTokenProvider jwtTokenProvider;

    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/api/v1/user/**").permitAll()
                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider)
                        , UsernamePasswordAuthenticationFilter.class);
    }

    public void configure(WebSecurity webSecurity){
        webSecurity.ignoring().antMatchers();
    }
}
