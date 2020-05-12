package com.mcubes.configuration.websecurity;

import com.mcubes.configuration.websecurity.handler.MyAuthenticationSuccessHandler;
import com.mcubes.configuration.websecurity.service.AdminDetailsService;
import com.mcubes.configuration.websecurity.service.MyUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

/**
 * Created by A.A.MAMUN on 2/4/2020.
 */

@Configuration
@EnableWebSecurity
@Order(2)
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        authenticationProvider.setUserDetailsService(myUserDetailsService);
        auth.authenticationProvider(authenticationProvider);
    }


        /*
        @Bean
        public DaoAuthenticationProvider handleAuthenticationHandler(){

            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
            authenticationProvider.setUserDetailsService(myUserDetailsService);
            return authenticationProvider;
        }
        */

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/", "/home").permitAll()
                .antMatchers("/account/**", "/profile", "/wishlist", "/order", "/order-history", "/orderlist", "/checkout", "/cart", "/add-to-cart",
                        "/update-cart-item", "/delete-cart-item", "/remove-cart-item", "/add-to-wishlist", "/remove_wish",
                        "/post-blog-comment", "/product-available-validation")
                .access("hasRole('ROLE_CLIENT')")
                //.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .and()
                .formLogin()
                .loginPage("/login")
                //.loginProcessingUrl("/login")
                .defaultSuccessUrl("/")
                //.successHandler(myAuthenticationSuccessHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/")
                .and()
                .exceptionHandling().accessDeniedPage("/access?error");
    }

}


