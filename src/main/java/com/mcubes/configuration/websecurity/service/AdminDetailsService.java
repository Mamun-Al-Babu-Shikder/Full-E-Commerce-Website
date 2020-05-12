package com.mcubes.configuration.websecurity.service;

import com.mcubes.configuration.websecurity.details.AdminDetails;
import com.mcubes.configuration.websecurity.details.MyUserDetails;
import com.mcubes.entity.Admin;
import com.mcubes.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by A.A.MAMUN on 4/21/2020.
 */
@Service
public class AdminDetailsService implements UserDetailsService {

    @Autowired
    private AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Admin admin = adminRepository.findAdminByEmail(s);
        if(admin==null)
            throw new UsernameNotFoundException("Admin not found.");
        return new AdminDetails(admin);
    }
}
