package com.epam.esm.controller.security.configuration;

import com.epam.esm.controller.security.handler.CustomAuthenticationEntryPoint;
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

                and().authorizeRequests().

                antMatchers(HttpMethod.GET , "/certificates/*", "/tags/*", "/users/widely-used-tag",
                        "/certificates/by-name-and-description", "/certificates/by-tags", "/certificates").
                permitAll().

                antMatchers("/auth/login").
                permitAll().

                antMatchers("/auth/logout").
                permitAll().

                antMatchers(HttpMethod.POST, "/users/create-order").
                hasAuthority(
                        Permission.USER_CREATE_ORDER.getPermission()
                ).

                // FIXME: 2/19/21 ADMIN permissions
                antMatchers(HttpMethod.GET, "/users/*/orders", "/users").
                hasAnyAuthority(
                        Permission.USER_READ.getPermission(),
                        Permission.USER_ORDER_READ.getPermission()
                ).

                antMatchers(HttpMethod.POST,
                        "/certificates/create" ,"/certificates/*/update", "/certificates/*/delete",
                        "tags/*/delete", "/tags/create").
                hasAnyAuthority(
                    Permission.CERTIFICATE_DELETE.getPermission(),
                    Permission.CERTIFICATE_UPDATE.getPermission(),
                    Permission.CERTIFICATE_WRITE.getPermission(),
                    Permission.TAG_WRITE.getPermission(),
                    Permission.TAG_DELETE.getPermission()
                ).

                anyRequest().denyAll().

                and().
                exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint()).

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
