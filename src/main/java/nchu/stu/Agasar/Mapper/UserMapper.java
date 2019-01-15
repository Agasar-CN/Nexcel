package nchu.stu.Agasar.Mapper;

import nchu.stu.Agasar.Entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {
    @Insert("INSERT INTO user(email,username,password,code) VALUES(#{email},#{username},#{password},#{code})")
    int register(User user);
    @Select("SELECT * FROM user WHERE email=#{email}")
    User findUser(String email);
    @Update("UPDATE user SET status=1 WHERE email=#{email}")
    int activeUser(String email);
    @Select("SELECT * FROM user WHERE email=#{email} AND password=#{password}")
    User login(User user);
    @Select("SELECT email FROM user")
    List<String> findAllEmail();
}
