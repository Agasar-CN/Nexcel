package nchu.stu.Agasar.Service.impl;

import nchu.stu.Agasar.Entity.Post;
import nchu.stu.Agasar.Mapper.PostMapper;
import nchu.stu.Agasar.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Transactional(propagation=Propagation.REQUIRED,isolation=Isolation.DEFAULT)
@Service("postService")
public class PostServiceImpl implements PostService {
    @Autowired
    PostMapper postMapper;
    @Override
    public int uploadPost(Post post) {
        return postMapper.uploadPost(post);
    }

    @Override
    public List<String> findAllCode() {
        return postMapper.findAllCode();
    }

    @Override
    public List<Post> findPostE(String email) {
        return postMapper.findPostE(email);
    }

    @Override
    public int addNum(String code) {
        return postMapper.addPerson(code);
    }

    @Override
    public Post findPostC(String code) {
        return postMapper.findPostC(code);
    }
}
