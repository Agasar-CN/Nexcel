package nchu.stu.Agasar.Mapper;

import nchu.stu.Agasar.Entity.Model;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface  ModelMapper {
    @Insert("INSERT INTO model(code,modelname) VALUES(#{code},#{modelname})")
    int setTitleName(Model model);

    @Select("SELECT modelname FROM model WHERE code=#{code}")
    List<String> findAllTitleName(String code);
}
