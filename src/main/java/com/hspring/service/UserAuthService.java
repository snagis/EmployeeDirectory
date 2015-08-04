package com.hspring.service;

import com.hspring.entity.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by snagis on 8/2/15.
 */

@Service
public class UserAuthService implements UserDetailsService {

    private EmployeeRepository repo;

    @Autowired
    public UserAuthService(EmployeeRepository repo) {
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        Employee employee = repo.findByEmail(email);
        if (employee == null) {
            return null;
        }
        List<GrantedAuthority> auth = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");
        if ("ROLE_ADMIN".equals(employee.getRole())) {
            auth = AuthorityUtils
                    .commaSeparatedStringToAuthorityList("ROLE_ADMIN");
        }
        String password = employee.getPassword();
        return new org.springframework.security.core.userdetails.User(employee.getEmail(), password, auth);
    }
}

