package com.epam.esm.controller.confugration;

import com.epam.esm.model.entity.Permission;
import com.epam.esm.service.security.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
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

    // FIXME: 2/18/21 check this configuration
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable().
                sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                and().
                authorizeRequests().
                antMatchers(HttpMethod.GET , "/certificate/get/*", "/tag/get/*", "/user/widely_userd_tag").
                hasAnyAuthority(
                        Permission.CERTIFICATE_READ.getPermission(),
                        Permission.TAG_READ.getPermission(),
                        Permission.USER_WIDELY_USED_TAG.getPermission()
                ).

                antMatchers("/user/create/order").
                hasAuthority(
                        Permission.USER_CREATE_ORDER.getPermission()
                ).

                antMatchers(HttpMethod.GET, "/user/get/*", "/certificate/*", "/tag/*").
                hasAnyAuthority(
                        Permission.CERTIFICATE_DELETE.getPermission(),
                        Permission.CERTIFICATE_UPDATE.getPermission(),
                        Permission.CERTIFICATE_WRITE.getPermission(),
                        Permission.TAG_WRITE.getPermission(),
                        Permission.TAG_DELETE.getPermission(),
                        Permission.USER_READ.getPermission(),
                        Permission.USER_ORDER_READ.getPermission()
                ).

                antMatchers(HttpMethod.POST, "/auth/login").
                permitAll().

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
