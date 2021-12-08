package tacos;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import tacos.dao.UserDao;
import tacos.service.UserService;

import java.util.Arrays;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = TacoCloudApplication.class)
public class MybatisTest {

    @Autowired
    private UserService userService;

    @Test
    public void UserTest(){
        System.out.println(userService.findByUsername("hhh"));
    }
}
