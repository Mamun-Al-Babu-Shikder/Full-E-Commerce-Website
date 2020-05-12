package com.mcubes.configuration.websecurity.service;

import com.mcubes.configuration.websecurity.details.MyUserDetails;
import com.mcubes.entity.AppUser;
import com.mcubes.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by A.A.MAMUN on 2/4/2020.
 */
@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        AppUser appUser = appUserRepository.findAppUserByEmailAddress(s);
        if(appUser==null)
            throw new UsernameNotFoundException("User not found!");
       // System.out.println("# "+appUser.toString());
        return new MyUserDetails(appUser);
    }
}
