package com.epam.esm.controller.confugration;

import com.epam.esm.controller.role.Role;
import com.epam.esm.service.security.service.CustomUserDetailsService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                authorizeRequests().
                antMatchers(HttpMethod.GET , "/certificate/get/*", "/tag/get/*", "/user/widely_userd_tag").
                hasAnyRole(Role.ADMIN.name(), Role.GUEST.name(), Role.USER.name()).

                antMatchers("/user/create/order").hasRole(Role.USER.name()).

                antMatchers(HttpMethod.GET, "/user/get/*", "/certificate/*", "/tag/*").
                hasRole(Role.ADMIN.name()).

                anyRequest().authenticated().
                and().
                httpBasic();
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }
}
