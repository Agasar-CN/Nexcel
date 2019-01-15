package nchu.stu.Agasar.Controller;

import nchu.stu.Agasar.Component.Email;
import nchu.stu.Agasar.Component.MD5;
import nchu.stu.Agasar.Component.SampleMail;
import nchu.stu.Agasar.Entity.User;
import nchu.stu.Agasar.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import nchu.stu.Agasar.Component.EmailUtils;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/sign")
public class SignController {
    @Autowired
    @Qualifier("userService")
    private UserService userService;
    //中转所用
    @RequestMapping(value = "/turn/{var}")
    void turn(@PathVariable("var") String var,HttpServletRequest request,HttpServletResponse response){
        String info=null;
        switch (var){
            case "a":
                info="你已注册，还未激活请前往邮箱查收激活邮件！";
                break;
            case "b":
                info="注册失败,请联系管理员Agasar@163.com";
                break;
            case "c":
                info="激活成功，现在登入吧！";
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
            request.getRequestDispatcher("/").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //注册所用
    @RequestMapping(value ="/up",method = RequestMethod.POST)
    void register(String email, String username, String password, HttpServletRequest request,HttpServletResponse response) {
        User user = new User();
        int n = new Random().nextInt(900000) + 100000;
        try {
            password = MD5.EncoderByMd5(password);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String code = String.valueOf(n);
        email=email.toLowerCase();
        user.setEmail(email);
        user.setUsername(username);
        user.setPassword(password);
        user.setCode(code);
        try {
            if (userService.register(user) == 1) {
                //EmailUtils.sendMail(user);
                Email.sendHtmlMail(user);
                //SampleMail.sendMail(user);
                response.sendRedirect("turn/a");
            } else
                response.sendRedirect("turn/b");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/active",method = RequestMethod.GET)
    void Acvive(String email,String code,HttpServletResponse response){
        User user=userService.findUser(email);
        try {
            if (code.equals(user.getCode())) {
                userService.activeUser(email);
                System.out.println("ActiveSuc");
                response.sendRedirect("turn/c");
            } else {

            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
    @RequestMapping(value = "/in",method = RequestMethod.POST)
    void login(String email, String password, String check, HttpServletResponse response,
               HttpServletRequest request,HttpSession session) {
        User user = new User();
        email=email.toLowerCase();
        user.setEmail(email);
        try {
            password = MD5.EncoderByMd5(password);
            user.setPassword(password);
            if (userService.login(user) == null) {
                response.sendRedirect("turn/d");
                return;
            } else {
                int status=userService.findUser(email).getStatus();
                if (status==0) {
                    try {
                        response.sendRedirect("turn/e");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return;
                }
                session.setAttribute("user", user);
                if (check!=null&&check.equals(check)) {
                    Cookie cookie1 = new Cookie("email", email);
                    Cookie cookie2 = new Cookie("password", password);
                    //设置cookie的有效期
                    cookie1.setMaxAge(7200);
                    cookie2.setMaxAge(7200);
                    cookie1.setPath("/Nexcel");
                    cookie2.setPath("/Nexcel");
                    //保存cookie到客户端
                    response.addCookie(cookie1);
                    response.addCookie(cookie2);
                }
            }
            response.sendRedirect("/Nexcel/user/host");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }
    @RequestMapping(value = "/in",method = RequestMethod.GET)
    void autoLogin(String email, String password, HttpServletResponse response,
                   HttpServletRequest request,HttpSession session) {
        User user = new User();
        email=email.toLowerCase();
        user.setEmail(email);
        user.setPassword(password);
        if (userService.login(user) == null) {
            try {
                response.sendRedirect("sign/turn/d");
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        else{
            int status=userService.findUser(email).getStatus();
            if (status==0) {
                try {
                    response.sendRedirect("sign/turn/e");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return;
            }
            session.setAttribute("user", user);
            try {
                response.sendRedirect("/Nexcel/user/host");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping(value = "/sure",method = RequestMethod.GET)
    @ResponseBody
    String sure(String eml,HttpServletRequest request,HttpServletResponse response){
        eml=eml.toLowerCase();
        List<String> emls=userService.findAllEmail();
        String mesg=null;
        response.setContentType("text/html;charset=UTF-8");
        for(String temp:emls){
            System.out.println(temp);
            if(eml.equals(temp)){
                mesg="Please use another Email to registration";
                break;
            }
        }
        System.out.println(eml);
        System.out.println(mesg);
        return mesg;
    }
}
