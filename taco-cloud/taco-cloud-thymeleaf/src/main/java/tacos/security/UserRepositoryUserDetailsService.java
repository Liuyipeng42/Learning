package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.
        UserDetailsService;
import org.springframework.security.core.userdetails.
        UsernameNotFoundException;
import org.springframework.stereotype.Service;

import tacos.domain.User;
import tacos.service.UserService;

// 用于用户登录
@Service
public class UserRepositoryUserDetailsService implements UserDetailsService {

    private final UserService userService;

    @Autowired
    public UserRepositoryUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {

        User user = userService.findByUsername(username);

        if (user != null) {
            return user;
        }
        throw new UsernameNotFoundException(
                "User '" + username + "' not found");
    }
}
