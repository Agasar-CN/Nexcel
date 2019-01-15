package nchu.stu.Agasar.Service;

import nchu.stu.Agasar.Entity.Post;

import java.util.List;

public interface PostService {
    int uploadPost(Post post);
    List<String> findAllCode();
    Post findPostC(String code);
    List<Post> findPostE(String email);
    int addNum(String code);
}
