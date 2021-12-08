package tacos.service;

import tacos.domain.User;


public interface UserService {
    User findByUsername(String username);

    int saveUser(User user);
}
