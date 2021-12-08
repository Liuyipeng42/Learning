package tacos.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import tacos.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate jdbc;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public User findByUsername(String username) {
        try {
            return jdbc.queryForObject(
                    "select * from user where username=?",
                    this::mapRowToUser, username);
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    private User mapRowToUser(ResultSet rs, int rowNum) throws SQLException {
        return new User(
                rs.getString("id"),
                rs.getString("username"),
                rs.getString("password"),
                rs.getString("fullname"),
                rs.getString("street"),
                rs.getString("city"),
                rs.getString("state"),
                rs.getString("zip"),
                rs.getString("phoneNumber")
        );
    }

    @Override
    public User save(User user) {
        jdbc.update(
                "insert into user (username, password, fullname, street, city, state, zip, phoneNumber)" +
                        " values (?, ?, ?, ?, ?, ?, ?, ?)",
                user.getUsername(), user.getPassword(), user.getFullname(),
                user.getStreet(), user.getCity(), user.getState(), user.getZip(), user.getPhoneNumber());
        return user;
    }
}
