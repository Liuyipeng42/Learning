package com.mybatisplus.demo.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.entity.User;
import com.mybatisplus.demo.model.ParamSome;
import com.mybatisplus.demo.model.UserChildren;
import com.mybatisplus.demo.model.MyPage;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.RowBounds;

import java.util.List;
import java.util.Map;


//@Mapper
public interface UserMapper extends BaseMapper<User> {

    /**
     * 3.x 的 page 可以进行取值, 多个入参记得加上注解
     * 自定义 page 类必须放在入参第一位
     * 返回值可以用 IPage<T> 接收 也可以使用入参的 MyPage<T> 接收
     * <li> 3.1.0 之前的版本使用注解会报错,写在 xml 里就没事 </li>
     * <li> 3.1.0 开始支持注解,但是返回值只支持 IPage ,不支持 IPage 的子类</li>
     *
     * @param myPage 自定义 page
     * @return 分页数据
     */
//    @Select("select * from user where (age = #{pg.selectInt} and name = #{pg.selectStr}) or (age = #{ps.yihao} and name = #{ps.erhao})")
    MyPage<User> mySelectPage(@Param("pg") MyPage<User> myPage, @Param("ps") ParamSome paramSome);


    // 使用 Wrapper 自定义SQL
    // param 参数名要么叫ew, 要么加上注解@Param(Constants.WRAPPER) 使用${ew.customSqlSegment}
    // 不支持 Wrapper 内的entity生成where语句
    @Select("select * from user ${ew.customSqlSegment} and name = ${user.name}")
    List<User> getUser(@Param("user") User user, @Param(Constants.WRAPPER) Wrapper<User> wrapper);

    // 多表分页查询
    // @Select注解若想要使用 动态sql 必须要用 <script> </script>
    @ResultMap("userChildrenMap")
    @Select("<script>" +
            "select u.id,u.name,u.email,u.age,c.id as \"c_id\",c.name as \"c_name\",c.user_id as \"c_user_id\" " +
            "from user u " +
            "left join children c on c.user_id = u.id " +
            "<where>" +
                "<if test=\"selectInt != null\"> " +
                    "and u.age = #{selectInt} " +
                "</if> " +
                "<if test=\"selectStr != null and selectStr != ''\"> " +
                    "and c.name = #{selectStr} " +
                "</if> " +
            "</where>" +
            "</script>")
    MyPage<UserChildren> userChildrenPage(MyPage<UserChildren> myPage);

    MyPage<User> mySelectPageMap(MyPage<User> pg, @Param("map") Map<String, String> map);

    List<User> mySelectMap(Map<String, Object> param);

    List<User> myPageSelect(MyPage<User> myPage);

    List<User> iPageSelect(Page<User> myPage);

    List<User> rowBoundList(RowBounds rowBounds, Map<String, Object> map);
}