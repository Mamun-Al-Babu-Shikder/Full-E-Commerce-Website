package com.mcubes.configuration.websecurity;

import com.mcubes.configuration.websecurity.handler.AdminAuthenticationSuccessHandler;
import com.mcubes.configuration.websecurity.service.AdminDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

/**
 * Created by A.A.MAMUN on 4/21/2020.
 */
@Configuration
@EnableWebSecurity
@Order(1)
public class AdminWebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AdminDetailsService adminDetailsService;

    @Autowired
    private AdminAuthenticationSuccessHandler adminAuthenticationSuccessHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        daoAuthenticationProvider.setUserDetailsService(adminDetailsService);
        auth.authenticationProvider(daoAuthenticationProvider);
    }

    /*
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProviderHandler(){

        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
        daoAuthenticationProvider.setUserDetailsService(adminDetailsService);

        return daoAuthenticationProvider;
    }
    */


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .antMatcher("/admin2/**")
                .authorizeRequests()
                .antMatchers("/admin2/**")
                .access("hasRole('ROLE_ADMIN')")
                //.antMatchers("/admin/dist/**").permitAll()
                //.anyRequest()
               // .hasRole("ADMIN")
                .and()
                .formLogin()
                .loginPage("/admin/login").permitAll()
                .defaultSuccessUrl("/admin/dashboard")
                //.successHandler(adminAuthenticationSuccessHandler)
                .and()
                .logout()
                .logoutUrl("/logout")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/admin/login")
                .and()
                .exceptionHandling().accessDeniedPage("/access?error");

    }
}
