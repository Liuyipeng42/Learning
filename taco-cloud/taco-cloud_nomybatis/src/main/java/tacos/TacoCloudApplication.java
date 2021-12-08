package tacos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 引导类 6227612145830440
// 最小化的 Spring 配置，以引导程序运行
@SpringBootApplication
public class TacoCloudApplication implements WebMvcConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(TacoCloudApplication.class, args);
        // 执行引导过程，创建 Spring 的应用上下文
        // 第一个参数：要配置的类  第二个参数：命令行参数
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // addViewControllers()方法会接收一个 ViewControllerRegistry 对象，我们可以使用它
        // 注册一个或多个视图控制器。
        registry.addViewController("/").setViewName("home");
    }

}
