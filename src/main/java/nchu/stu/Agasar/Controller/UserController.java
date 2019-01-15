package nchu.stu.Agasar.Controller;

import nchu.stu.Agasar.Component.Nexcel;
import nchu.stu.Agasar.Entity.Model;
import nchu.stu.Agasar.Entity.Post;
import nchu.stu.Agasar.Entity.User;
import nchu.stu.Agasar.Service.ModelService;
import nchu.stu.Agasar.Service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    @Qualifier("postService")
    private PostService postService;
    @Autowired
    @Qualifier("modelService")
    private ModelService modelService;
    @RequestMapping(value = "/turn/{var}")
    void turn(@PathVariable("var") String var, HttpServletRequest request, HttpServletResponse response){
        String info=null;
        switch (var){
            case "a":
                info="上传成功任务成功！";
                break;
            case "b":
                info="上传的文件为空，请选择文件上传";
                break;
            case "c":
                info="文件类型不合法！其请上传xlsx或xls类文件";
                break;
            case "d":
                info="Email或Password错误，请重新输入！";
                break;
            case "e":
                info="你的账号还未激活，请登陆自己的邮箱进行验证";
                break;
            case "f":
                info="试图非法登入，请注册账号后登入";
                break;
        }
        request.setAttribute("info",info);
        try {
            request.getRequestDispatcher("/user/host").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/host")
    String host( HttpServletResponse response, HttpServletRequest request,HttpSession session){
        if(session.getAttribute("user")==null){
            try {
                response.sendRedirect("/Nexcel/sign/turn/f");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }else {
            User user = (User) session.getAttribute("user");
            List<Post> list = postService.findPostE(user.getEmail());
            request.setAttribute("posts", list);
            return "Host";
        }
    }

    @RequestMapping(value="/upload",method=RequestMethod.POST)
    public void upload(MultipartFile file, HttpServletRequest request, HttpSession session,HttpServletResponse response) throws IOException {
        String fileName = file.getOriginalFilename();
        if(fileName!=null&&fileName.equals("")){
            response.sendRedirect("/Nexcel/user/turn/b");
            return;
        }
        String[] s=fileName.split("\\.");
        if(!s[s.length-1].equals("xlsx")&&!s[s.length-1].equals("xls")){
            response.sendRedirect("/Nexcel/user/turn/c");
            return;
        }
        String code=null;
        //确保邀请码不会重复
        do{
            int n = new Random().nextInt(900000) + 100000;
            code=String.valueOf(n);
        }while (postService.findPostC(code)!=null);
        User user = (User) session.getAttribute("user");
        Post post=new Post();
        post.setEmail(user.getEmail());
        post.setCode(code);
        post.setPostname(fileName);
        String path = request.getSession().getServletContext().getRealPath(File.separator+"WEB-INF"+File.separator+"upload"+File.separator+code);
        post.setFile(path+File.separator+fileName);
        File dir = new File(path,fileName);
        if(!dir.exists()){
            dir.mkdirs();
        }
        //MultipartFile自带的解析方法
        file.transferTo(dir);
        postService.uploadPost(post);
        List<String> titlename=null;
        try {
            titlename= Nexcel.createTittle(post.getFile());
            Model model=new Model();
            model.setCode(code);
            for(String temp:titlename){
                model.setModelname(temp);
                modelService.setTitleName(model);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("/Nexcel/user/turn/a");
    }
    @RequestMapping(value = "/down",method =RequestMethod.GET)
    public void down(String code, HttpServletRequest request,HttpServletResponse response) throws Exception{
        Post post=postService.findPostC(code);
        //模拟文件，myfile.txt为需要下载的文件
        String fileName = post.getFile();
        //获取输入流
        InputStream bis = new BufferedInputStream(new FileInputStream(new File(fileName)));
        //假如以中文名下载的话
        String filename = post.getPostname();
        //转码，免得文件名中文乱码
        filename = URLEncoder.encode(filename,"UTF-8");
        //设置文件下载头
        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
        //1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
        response.setContentType("multipart/form-data");
        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        int len = 0;
        while((len = bis.read()) != -1){
            out.write(len);
            out.flush();
        }
        out.close();
    }
}
