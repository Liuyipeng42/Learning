package com.mybatisplus.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.entity.User;
import com.mybatisplus.demo.mapper.UserMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class CURDTest {

    // mapper是在 service 中用的，然后再在 controller 中使用 service
    // mapper CURD 和 service CURD 接口 都有关于增删查改的方法，service CURD 的要更加丰富一些
    // 在 controller 中可以直接使用 service 中自带的方法，若是满足不了需求
    // 就在 service中利用 mapper CURD 接口 编写特定的 service 层的方法

    @Resource
    private UserMapper mapper;

    @Test
    public void aInsert() {
        User user = new User();
        user.setName("Sandy");
        user.setAge(3);
        user.setEmail("abc@mp.com");
        assertThat(mapper.insert(user)).isGreaterThan(0);
        // 成功直接拿回写的 ID
        System.out.println(user.getId());
        assertThat(user.getId()).isNotNull();
    }

    @Test
    public void bDelete() {
        mapper.deleteById(3L);
        mapper.delete(
                new QueryWrapper<User>().lambda().eq(User::getName, "Sandy")
        );
        // ==>  Preparing: DELETE FROM user WHERE (name = ?)
        // ==> Parameters: Sandy(String)
    }


    @Test
    public void cUpdate() {
        mapper.updateById(
                new User().setId(1L).setEmail("ab@c.c")
        );

        // 第一个 Entity(User) 对象 中的属性表示 sql 中 set 的部分
        // set方法 也可以添加sql 中 set 的部分，若有的字段在 Entity(User) 对象的属性中没有，就可以用这个反法
        // eq方法 表示 where 后面的 相等条件
        mapper.update(
                new User().setName("mp"),
                Wrappers.<User>lambdaUpdate()
                        .set(User::getAge, 3)
                        .eq(User::getId, 2)
        );
        // ==>  Preparing: UPDATE user SET name=?, age=? WHERE (id = ?)
        // ==> Parameters: mp(String), 3(Integer), 2(Integer)

        mapper.update(
                null,
                Wrappers.<User>lambdaUpdate()
                        .set(User::getEmail, null)
                        .eq(User::getId, 2)
        );
        // ==>  Preparing: UPDATE user SET email=? WHERE (id = ?)
        // ==> Parameters: null, 2(Integer)

        mapper.update(
                new User().setEmail("miemie@baomidou.com"),
                new QueryWrapper<User>()
                        .lambda().eq(User::getId, 2)
        );
        // ==>  Preparing: UPDATE user SET email=? WHERE (id = ?)
        // ==> Parameters: miemie@baomidou.com(String), 2(Integer)

        mapper.update(
                new User().setEmail("miemie2@baomidou.com"),
                Wrappers.<User>lambdaUpdate()
                        .set(User::getAge, null)
                        .eq(User::getId, 2)
        );
        // ==>  Preparing: UPDATE user SET email=?, age=? WHERE (id = ?)
        // ==> Parameters: miemie2@baomidou.com(String), null, 2(Integer)

    }

    @Test
    public void selectNull() {
        User user = mapper.selectOne(
                new QueryWrapper<User>().lambda().eq(User::getId, 10086)
        );
        System.out.println(user);
    }

    @Test
    public void dSelect() {
        mapper.insert(
                new User().setId(10086L)
                        .setName("miemie")
                        .setEmail("miemie@baomidou.com")
                        .setAge(3)
        );

        assertThat(mapper.selectById(10086L).getEmail()).isEqualTo("miemie@baomidou.com");

        User user = mapper.selectOne(
                new QueryWrapper<User>().lambda().eq(User::getId, 10086)
        );
        assertThat(user.getName()).isEqualTo("miemie");
        assertThat(user.getAge()).isEqualTo(3);

        // lambdaQuery 返回一个 LambdaQueryWrapper
        // 在 LambdaQueryWrapper 中 字段名是一个方法，如 User::getId
        // 在 QueryWrapper 中 字段名是一个字符串，字符串就是字段名
        // (lambdaUpdate) lambdaUpdate 与 UpdateWrapper 同理
        mapper.selectList(
                Wrappers.<User>lambdaQuery().select(User::getId)
        ).forEach(x -> {
            assertThat(x.getId()).isNotNull();
            assertThat(x.getEmail()).isNull();
            assertThat(x.getName()).isNull();
            assertThat(x.getAge()).isNull();
        });

        mapper.selectList(
                new QueryWrapper<User>().select("id", "name")
        ).forEach(x -> {
            assertThat(x.getId()).isNotNull();
            assertThat(x.getEmail()).isNull();
            assertThat(x.getName()).isNotNull();
            assertThat(x.getAge()).isNull();
        });
    }

    @Test
    public void orderBy() {
        List<User> users = mapper.selectList(
                Wrappers.<User>query().orderByAsc("age")
        );
        assertThat(users).isNotEmpty();

        //多字段排序
        List<User> users2 = mapper.selectList(
                Wrappers.<User>query().orderByAsc("age", "name")
        );
        assertThat(users2).isNotEmpty();

        //先按age升序排列，age相同再按name降序排列
        List<User> users3 = mapper.selectList(
                Wrappers.<User>query().orderByAsc("age").orderByDesc("name")
        );
        assertThat(users3).isNotEmpty();
    }

    @Test
    public void selectMaps() {
        // 查询结果转化为 map
        List<Map<String, Object>> mapList = mapper.selectMaps(
                Wrappers.<User>query().orderByAsc("age")
        );
        assertThat(mapList).isNotEmpty();
        assertThat(mapList.get(0)).isNotEmpty();
        System.out.println(mapList.get(0));
    }

    @Test
    public void selectMapsPage() {
        IPage<Map<String, Object>> page = mapper.selectMapsPage(
                new Page<>(1, 5),
                Wrappers.<User>query().orderByAsc("age")
        );
        assertThat(page).isNotNull();
        assertThat(page.getRecords()).isNotEmpty();
        assertThat(page.getRecords().get(0)).isNotEmpty();
        System.out.println(page.getRecords().get(0));
    }

    @Test
    @SuppressWarnings("unchecked") // 消除一个警告
    public void orderByLambda() {
        List<User> users = mapper.selectList(
                Wrappers.<User>lambdaQuery().orderByAsc(User::getAge)
        );
        assertThat(users).isNotEmpty();

        //多字段排序
        List<User> users2 = mapper.selectList(
                Wrappers.<User>lambdaQuery().orderByAsc(User::getAge, User::getName)
        );
        assertThat(users2).isNotEmpty();

        //先按age升序排列，age相同再按name降序排列
        List<User> users3 = mapper.selectList(
                Wrappers.<User>lambdaQuery().orderByAsc(User::getAge).orderByDesc(User::getName)
        );
        assertThat(users3).isNotEmpty();
    }

    @Test
    public void testSelectMaxId() {
        // select 可以直接 输入想要查找的字段
        User user = mapper.selectOne(
                new QueryWrapper<User>().select("max(id) as id")
        );
        System.out.println("maxId=" + user.getId());

        List<User> users = mapper.selectList(
                Wrappers.<User>lambdaQuery().orderByDesc(User::getId)
        );
        Assert.assertEquals(user.getId().longValue(), users.get(0).getId().longValue());
    }

    @Test
    public void testGroup() {
        List<Map<String, Object>> mapList = mapper.selectMaps(
                new QueryWrapper<User>().select("age", "count(*)").groupBy("age")
        );
        System.out.println();
        mapList.forEach(System.out::println);
        System.out.println();

        // lambdaQueryWrapper groupBy orderBy
        List<User> userList = mapper.selectList(
                new QueryWrapper<User>().lambda()
                        .select(User::getAge)
                        .groupBy(User::getAge)
                        .orderByAsc(User::getAge)
        );
        System.out.println();
        userList.forEach(System.out::println);
        System.out.println();
    }

    @Test
    public void testTableFieldExistFalse() {
        List<User> list = mapper.selectList(
                new QueryWrapper<User>().select("age", "count(age) as count").groupBy("age")
        );
        list.forEach(System.out::println);
        list.forEach(x -> {
            Assert.assertNull(x.getId());
            Assert.assertNotNull(x.getAge());
            Assert.assertNotNull(x.getCount());
        });

        mapper.insert(
                new User().setId(10088L)
                        .setName("miemie")
                        .setEmail("miemie@baomidou.com")
                        .setAge(3));
        User miemie = mapper.selectById(10088L);
        Assert.assertNotNull(miemie);

    }

    @Test
    public void customSql(){
        System.out.println();
        mapper.getUser(new User().setName("'Jack'"), new QueryWrapper<User>().le("id", "100"))
        .forEach(System.out::println);
        System.out.println();
    }

}