package nchu.stu.Agasar.Service;

import nchu.stu.Agasar.Entity.User;

import java.util.List;

public interface UserService {
    //register
    int register(User user);
    //finUser
    User findUser(String email);
    //ActiveUser
    int activeUser(String email);
    //login
    User login(User user);
    //finaAllUser
    List<String> findAllEmail();
}
