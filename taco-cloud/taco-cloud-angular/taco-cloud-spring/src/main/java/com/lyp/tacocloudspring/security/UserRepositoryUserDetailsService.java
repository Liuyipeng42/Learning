package com.lyp.tacocloudspring.security;

import com.lyp.tacocloudspring.entity.User;
import com.lyp.tacocloudspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserRepositoryUserDetailsService
        implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserRepositoryUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user != null) {
            return (UserDetails) user;
        }
        throw new UsernameNotFoundException(
                "User '" + username + "' not found");
    }

}
