package nchu.stu.Agasar.Service.impl;

import nchu.stu.Agasar.Entity.User;
import nchu.stu.Agasar.Mapper.UserMapper;
import nchu.stu.Agasar.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int register(User user) {
        return userMapper.register(user);
    }

    @Override
    public User findUser(String email) {
        return userMapper.findUser(email);
    }

    @Override
    public int activeUser(String email) {
        return userMapper.activeUser(email);
    }

    @Override
    public List<String> findAllEmail() {
        return userMapper.findAllEmail();
    }

    @Override
    public User login(User user) {
        return userMapper.login(user);
    }
}
