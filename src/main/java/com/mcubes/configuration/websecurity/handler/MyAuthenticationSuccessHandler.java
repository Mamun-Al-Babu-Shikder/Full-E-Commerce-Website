package com.mcubes.configuration.websecurity.handler;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by A.A.MAMUN on 2/19/2020.
 */
@Component
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {


        RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
        String role = authentication.getAuthorities().toArray()[0].toString();
        System.out.println("# Login Success! : "+role );

        System.out.println("# Redirect : "+httpServletRequest.getHeader("referer"));

        //response.sendRedirect(request.getHeader("referer");
        redirectStrategy.sendRedirect(httpServletRequest, httpServletResponse,"/");



    }
}
