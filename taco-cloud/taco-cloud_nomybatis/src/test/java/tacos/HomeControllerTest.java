package tacos;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

//@RunWith(SpringRunner.class) // 通过此注解可以告诉 JUnit 用什么进行测试，此处使用的是 SpringRunner
//@WebMvcTest(HomeController.class)
@WebMvcTest()
// 针对 HomeController 的 web测试，它会将 HomeController 注册到 Spring MVC 中
// 是 Spring Boot 所提供的一个特殊测试注解，它会让这个测试在 Spring MVC 应用的上下文中执行
public class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc; // 注入 MockMvc

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/")) // 发起对 “/” 的 GET 请求
                .andExpect(status().isOk()) // 期望得到 HTTP 200
                .andExpect(view().name("home")) // 期望得到 home 视图
                .andExpect(
                        content().string(containsString("Welcome to..."))
                ); // 期望包含 “Welcome to...”
    }

}
