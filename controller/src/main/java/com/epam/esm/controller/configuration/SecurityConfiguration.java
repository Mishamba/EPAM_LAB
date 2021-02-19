package com.epam.esm.controller.configuration;

import com.epam.esm.model.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final JwtConfigurer jwtConfigurer;

    @Autowired
    public SecurityConfiguration(JwtConfigurer jwtConfigurer) {
        this.jwtConfigurer = jwtConfigurer;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).

                /*and().anonymous().authorities(
                        Permission.CERTIFICATE_READ.getPermission(),
                        Permission.TAG_READ.getPermission(),
                        Permission.USER_WIDELY_USED_TAG.getPermission()
                ).*/

                and().authorizeRequests().

                antMatchers(HttpMethod.GET , "/certificate/get/*", "/tag/get/*", "/user/widely_userd_tag").
                permitAll().

                /*hasAnyAuthority(
                        Permission.CERTIFICATE_READ.getPermission(),
                        Permission.TAG_READ.getPermission(),
                        Permission.USER_WIDELY_USED_TAG.getPermission()
                ).*/

                antMatchers(HttpMethod.GET , "/certificate/get/*", "/tag/get/*", "/user/widely_userd_tag").
                permitAll().

                antMatchers("/auth/login").
                permitAll().

                antMatchers("/auth/logout").
                permitAll().

                and().
                authorizeRequests().
                antMatchers("/user/create/order").
                hasAuthority(
                        Permission.USER_CREATE_ORDER.getPermission()
                ).

                // FIXME: 2/19/21 ADMIN permissions
                antMatchers(HttpMethod.GET, "/user/get/*", "/certificate/update/*", "/certificate/delete/*",
                        "tag/delete/*", "/tag/create", "/user/create/order", "/user/get/user_orders", "/user/get/all").
                hasAnyAuthority(
                        Permission.CERTIFICATE_DELETE.getPermission(),
                        Permission.CERTIFICATE_UPDATE.getPermission(),
                        Permission.CERTIFICATE_WRITE.getPermission(),
                        Permission.TAG_WRITE.getPermission(),
                        Permission.TAG_DELETE.getPermission(),
                        Permission.USER_READ.getPermission(),
                        Permission.USER_ORDER_READ.getPermission()
                ).
                anyRequest().authenticated().
                and().
                apply(jwtConfigurer);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}
