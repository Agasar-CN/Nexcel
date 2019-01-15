package nchu.stu.Agasar.Service.impl;

import nchu.stu.Agasar.Entity.Model;
import nchu.stu.Agasar.Mapper.ModelMapper;
import nchu.stu.Agasar.Service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("modelService")
public class ModelServiceImpl implements ModelService {
    @Autowired
    ModelMapper modelMapper;
    @Override
    public int setTitleName(Model model) {
        return modelMapper.setTitleName(model);
    }

    @Override
    public List<String> findAllTitleName(String code) {
        return modelMapper.findAllTitleName(code);
    }
}
