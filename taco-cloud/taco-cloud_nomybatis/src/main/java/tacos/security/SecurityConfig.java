package tacos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // configure()方法接受一个 HttpSecurity 对象，能够用来配置在 Web 级别该如何处理
        //安全性。我们可以使用 HttpSecurity 配置的功能包括：
        //1. 在为某个请求提供服务之前，需要预先满足特定的条件；
        //2. 配置自定义的登录页；
        //3. 支持用户退出应用；
        //4. 预防跨站请求伪造。

//        http.csrf().disable();  // 关闭 csrf 防御
        http
                .authorizeRequests()
                .antMatchers("/orders", "/design")
                .hasRole("USER")
                // 具备 ROLE_USER 权限的用户才能访问“/design”和“/orders”；
                .antMatchers("/", "/**").permitAll()

                .and()
                .formLogin()
                .loginPage("/login") // 设置登录界面
                .defaultSuccessUrl("/design") // 登录成功后跳转到 /design

                .and()
                .logout()
                .logoutSuccessUrl("/"); // 退出登录成功后跳转到 /

//        http
//                .authorizeRequests()
//                // authorizeRequests()的调用会返回一个对象（ExpressionInterceptUrlRegistry），
//                // 基于它我们可以指定 URL 路径和这些路径的安全需求。
//                .antMatchers("/design", "/orders")
//                .hasRole("USER")
//                // 具备 ROLE_USER 权限的用户才能访问“/design”和“/orders”；
//                .antMatchers("/", "/**").permitAll()
//                // 其他的请求允许所有用户访问。
//                // 些规则的顺序很重要。声明在前面的安全规则比后面声明的规则有更高的优先级。
//                .and()
//                // and()方法将这一部分的配置与前面的配置连接在一起。
//                // and()方法表示我们已经完成了授权相关的配置，并且要添加一些其他的 HTTP 配置。
//                // 在开始新的配置区域时，我们可以多次调用 and()。
//                .formLogin()
//                // 调用 formLogin()开始配置自定义的登录表单
//                .loginPage("/login")
//                // 对loginPage()的调用声明了我们提供的自定义登录页面的路径。
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .loginProcessingUrl("/authenticate")
//                .usernameParameter("user")
//                .passwordParameter("pwd")
//                // 声明 Spring Security 要监听对“/authenticate”的请求来处理登录信息的提交。
//                // 同时，用户名和密码的字段名应该是 user 和 pwd
//                .and()
//                .formLogin()
//                .loginPage("/login")
//                .defaultSuccessUrl("/design")
//                // 用户直接导航至登录页并且成功登录之后将会被定向到“/design”页面。
////                .defaultSuccessUrl("/design", true);
//                // 强制要求用户在登录成功之后统一访问设计页面，即便用户在登
//                 //录之前正在访问其他页面，在登录之后也会被定向到设计页面，
//                .and()
//                .logout()
//                .logoutSuccessUrl("/")
//                // 退出功能
//                .and()
//                .logout()
//                .logoutSuccessUrl("/");
//                // 默认情况下，用户会被重定向到登录页面
//                // 可以调用 logoutSuccessUrl()指定退出后的不同页面

        // 基于内存的用户存储
//        auth.inMemoryAuthentication()
//                .passwordEncoder(new BCryptPasswordEncoder()) // 将前端传过来的密码进行某种方式加密
//                .withUser("buzz")
//                .password(new BCryptPasswordEncoder().encode("123")) // 将想要设置的密码进行加密，这样才可以做到与前端传过来的正确密码
//                .authorities("ROLE_USER") // 权限等级
//                .and()
//                .withUser("woody")
//                .password(new BCryptPasswordEncoder().encode("456"))
//                .authorities("ROLE_USER");

        // 基于 JDBC 的用户存储
//        auth
//                .jdbcAuthentication()
//                .dataSource(dataSource)
//                .usersByUsernameQuery(
//                        "select username, password, enabled from Users " +
//                                "where username=?")
//                .authoritiesByUsernameQuery(
//                        "select username, authority from UserAuthorities " +
//                                "where username=?")
//                .passwordEncoder(new BCryptPasswordEncoder().encode("456"));

        // 以 LDAP 作为后端的用户存储
//        auth
//                .ldapAuthentication()
//                .userSearchBase("ou=people") // 为基础 LDAP 查询提供过滤条件, 用于搜索用户
//                .userSearchFilter("(uid={0})") // 为基础 LDAP 查询提供过滤条件, 用于搜索组
//                .groupSearchBase("ou=groups")
//                .groupSearchFilter("member={0}")
//                .passwordCompare()
//                .passwordEncoder(new BCryptPasswordEncoder()) // 加密
//                .passwordAttribute("passcode");

        // 自定义
//        auth
//                .userDetailsService(userDetailsService)
//                .passwordEncoder(encoder());

    }
}
