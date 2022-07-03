package com.kndoni.event.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
//@EnableWebSecurity
public class WebSecurityConfig {

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//                //starts authorizing configuration
//                .authorizeRequests()
//                //ignoring the guests url
//                .antMatchers("/**").permitAll()
//                .anyRequest().fullyAuthenticated();
//
//    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*")
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
