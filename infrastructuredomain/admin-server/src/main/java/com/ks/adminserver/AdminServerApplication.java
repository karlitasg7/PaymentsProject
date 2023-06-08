package com.ks.adminserver;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
@EnableAdminServer
@EnableEurekaClient
public class AdminServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminServerApplication.class, args);
    }

    @Configuration
    public static class SecurityPermitAllConfig {// extends WebSecurityConfigurerAdapter {

        //        @Override
//        protected void configure(HttpSecurity httpSecurity) throws Exception {
//            httpSecurity.authorizeRequests()
//                    .anyRequest().permitAll()
//                    .and()
//                    .csrf()
//                    .disable();
//        }
        @Bean
        protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            return http
                    .authorizeHttpRequests((authorizeRequests) -> authorizeRequests
                            .anyRequest()
                            .permitAll()
                    )
                    .csrf()
                    .disable()
                    .build();
        }
    }

}
