package nchu.stu.Agasar.Service;

import nchu.stu.Agasar.Entity.Model;

import java.util.List;

public interface ModelService {
    int setTitleName(Model model);
    List<String> findAllTitleName(String code);
}
