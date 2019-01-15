package nchu.stu.Agasar.Mapper;

import nchu.stu.Agasar.Entity.Post;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface PostMapper {
    @Insert("INSERT INTO post(email,postname,code,file) VALUES(#{email},#{postname},#{code},#{file})")
    int uploadPost(Post post);

    @Update("UPDATE post SET num=num+1 WHERE code=#{code}")
    int addPerson(String code);

    @Select("SELECT code FROM post")
    List<String> findAllCode();

    @Select("SELECT * FROM post WHERE code=#{code}")
    Post findPostC(String code);

    @Select("SELECT * FROM post WHERE email=#{email}")
    List<Post> findPostE(String email);
}
