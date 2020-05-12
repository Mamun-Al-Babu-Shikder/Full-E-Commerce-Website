package com.mcubes.configuration.websecurity.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by A.A.MAMUN on 4/21/2020.
 */
@Component
public class AdminAuthenticationSuccessHandler implements AuthenticationSuccessHandler {


    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {

        authentication.getAuthorities().stream().forEach(o -> System.out.println("# "+o));
        System.out.println("# "+authentication.isAuthenticated());
        System.out.println("# "+authentication.getDetails().toString());
        System.out.println("# "+authentication.getPrincipal().toString());
        System.out.println("# "+authentication.getName().toString());
       // System.out.println("# "+authentication.getCredentials().toString());

    }
}
