package nchu.stu.Agasar.Controller;

import nchu.stu.Agasar.Component.Nexcel;
import nchu.stu.Agasar.Entity.Post;
import nchu.stu.Agasar.Entity.User;
import nchu.stu.Agasar.Service.ModelService;
import nchu.stu.Agasar.Service.PostService;
import org.apache.taglibs.standard.extra.spath.Step;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/guest")
public class GuestController {
    @Autowired
    @Qualifier("postService")
    private PostService postService;
    @Autowired
    @Qualifier("modelService")
    private ModelService modelService;
    @RequestMapping(value = "/sure",method = RequestMethod.GET)
    @ResponseBody
    String test(String invite){
        List<String> list=postService.findAllCode();
        String mesg="wrong";
        int flag=0;
        for(String str:list){
            if(str.equals(invite)){
                flag=1;
                break;
            }
        }
        if(flag==1)
            mesg="right";
        return mesg;
    }
    @RequestMapping(value = "/submit",method = RequestMethod.POST)
    String submit(String code, HttpServletRequest request, HttpSession session){
        List<String> list=modelService.findAllTitleName(code);
        session.setAttribute("code",code);
        request.setAttribute("model",list);
        return "Guest";
    }
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    String add(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        String[] strings=request.getParameterValues("text");
        List<String> list=new ArrayList<>();
        for (String str:strings)
            list.add(str);
        String code=(String)session.getAttribute("code");
        Post post=postService.findPostC(code);
        postService.addNum(code);
        try {
            Nexcel.add(1,2,list,post.getFile());
        } catch (Exception e) {
            try {
                Thread.sleep(2000);
                Nexcel.add(1,2,list,post.getFile());
            } catch (Exception e1) {
                try {
                    Thread.sleep(2000);
                    Nexcel.add(1,2,list,post.getFile());
                } catch (Exception e2) {
                    e1.printStackTrace();
                }
            }
        }
        session.invalidate();
        return "OK!You are job is done! You did Great";
    }
}
