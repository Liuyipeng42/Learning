package tacos.data;
//import org.springframework.data.repository.CrudRepository;
import tacos.domain.User;

//public interface UserRepository extends CrudRepository<User, Long> {
//
//    User findByUsername(String username);
//
//}
public interface UserRepository {

    User findByUsername(String username);

    User save(User user);

}
