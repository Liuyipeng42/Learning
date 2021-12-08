package tacos.dao;

import org.apache.ibatis.annotations.Mapper;
import tacos.domain.User;


@Mapper
public interface UserDao {
    User findByUsername(String username);

    int saveUser(User user);
}
