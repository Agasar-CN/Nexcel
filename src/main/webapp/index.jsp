<%--
  Created by IntelliJ IDEA.
  User: 10390
  Date: 2018/7/23
  Time: 10:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Nexcel</title>
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://cdn.bootcss.com/jquery/2.1.1/jquery.min.js"></script>
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
<%
    String email = null;
    String password = null;
    //获取cookie
    Cookie[] cookies = request.getCookies();
    if(cookies!=null){
        //如果cookies不为空的话遍历
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("email")){
                email = cookie.getValue();//获取email的值
            }
            if(cookie.getName().equals("password")){
                password = cookie.getValue();//获取password的值
            }
        }
    }
    //如果存在cookie的话不需要输入用户名和密码自己登陆
    if(email!=null && password!=null){
        request.getRequestDispatcher("/sign/in?email="+email+"&password="+password).forward(request, response);
    }

%>
<div class="container">
    <div class="page-header">
        <h1>
            Nexcel <small>Create Net Excel</small>
        </h1>
    </div>
    <div class="row clearfix">
        <div class="col-md-6 column">
            <div class="jumbotron">
                <h1>
                    Welcome to<br>
                    Nexcel!
                </h1>
                <p>
                    希望你可以花出你一点宝贵的时间来阅读以下消息。<br>
                    这个小程序，它可以让您在短时间内在大家的帮助下生成一个简易的Excel表格。如果您是一个表格的参与者，您只需要将你持有的邀请码填入下方，然后填入信息<strong>（注意时间格式)</strong>。<br>
                    如果您是一个Excel表格的创建者，那请您就在右边注册成为一个用户后，发起一次任务申请。<br>
                    谢谢您的配合！希望这个程序可以让你工作更加方便。
                </p>
                <form action="/Nexcel/guest/submit" method="post">
                    <input class="form-control"  id="invite" type="text" name="code" onblur="cheackInvite(this)" required="required"/>
                    <button type="submit" id="button" class="btn btn-block btn-primary btn-default" disabled="disabled">提交邀请码</button>
                </form>
            </div>
        </div>
        <div class="col-md-6 column">
            <div class="alert alert-dismissable alert-info">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4>
                    注意！
                </h4>
                <strong id="str">${requestScope.info}</strong>
                <h5>如果想要发起一个任务邀请，请登陆！</h5>
                <!--a href="#" class="alert-link">alert link</a-->
            </div>
            <div class="tabbable" id="tabs-733828">
                <ul class="nav nav-tabs">
                    <li class="active">
                        <a href="#panel-403796" data-toggle="tab">Login</a>
                    </li>
                    <li>
                        <a href="#panel-399299" data-toggle="tab">Register</a>
                    </li>
                </ul>
                <div class="tab-content">
                    <div class="tab-pane active" id="panel-403796">
                        <form class="form-horizontal" role="form" action="/Nexcel/sign/in" method="post">
                            <div class="form-group">
                                <label for="inputEmail1" class="col-sm-2 control-label">Email</label>
                                <div class="col-sm-10">
                                    <input class="form-control" id="inputEmail1" type="email" name="email" required="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword1" class="col-sm-2 control-label">Password</label>
                                <div class="col-sm-10">
                                    <input class="form-control" id="inputPassword1" type="password" name="password" required="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <div class="checkbox">
                                        <label><input type="checkbox" name="check"/>Remember me</label>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" id="button1" class="btn btn-default btn-primary">Login</button>
                                    </p>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="tab-pane" id="panel-399299">
                        <form class="form-horizontal" role="form" action="/Nexcel/sign/up" method="post">
                            <div class="form-group">
                                <label for="inputEmail2" class="col-sm-2 control-label">Email</label>
                                <div class="col-sm-10">
                                    <input class="form-control" id="inputEmail2" type="email" name="email" onblur="cheackEmail(this)" required="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputText2" class="col-sm-2 control-label">Username</label>
                                <div class="col-sm-10">
                                    <input class="form-control" id="inputText2" type="text" name="username" required="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="inputPassword2" class="col-sm-2 control-label">Password</label>
                                <div class="col-sm-10">
                                    <input class="form-control" id="inputPassword2" type="password" name="password" required="required"/>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <button type="submit" id="button2" class="btn btn-default btn-primary">Register</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="page-header">
    </div>
    <div align="center">
        Copyright &copy;2018-2020,Nchu.Stu.Agasar All Rights Reserved.
    </div>
</div>
<script>
    function reset(obj) {
        document.getElementById("str").innerHTML="";
        if(obj.id=="inputEmail1")
            document.getElementById("button1").className="btn btn-default btn-primary";
        else
            document.getElementById("button2").className="btn btn-default btn-primary";
    }
    function cheackEmail(obj)
    {
        var xmlhttp;
        if (window.XMLHttpRequest)
        {
            // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
            xmlhttp=new XMLHttpRequest();
        }
        else
        {
            // IE6, IE5 浏览器执行代码
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.open("GET","/Nexcel/sign/sure?eml="+obj.value,true);
        xmlhttp.send();
        xmlhttp.onreadystatechange=function()
        {
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
                var str=xmlhttp.responseText;
                document.getElementById("str").innerHTML=str;
                if(str!=""){
                    obj.value="";
                }
                if(obj.value=="")
                        document.getElementById("button2").className="btn btn-default btn-danger disabled";
                else
                        document.getElementById("button2").className="btn btn-default btn-primary";
            }
        }
    }function reset(obj) {
        document.getElementById("str").innerHTML="saaaaaaawqqw";
        if(obj.id=="inputEmail1")
            document.getElementById("button1").className="btn btn-default btn-primary";
        else
            document.getElementById("button2").className="btn btn-default btn-primary";
    }
    function add(){
        document.getElementById("button").disabled="disabled";
    }
    function cheackInvite(obj)
    {
        var xmlhttp;
        if (window.XMLHttpRequest)
        {
            // IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
            xmlhttp=new XMLHttpRequest();
        }
        else
        {
            // IE6, IE5 浏览器执行代码
            xmlhttp=new ActiveXObject("Microsoft.XMLHTTP");
        }
        xmlhttp.open("GET","/Nexcel/guest/sure?invite="+obj.value,true);
        xmlhttp.send();
        xmlhttp.onreadystatechange=function()
        {
            if (xmlhttp.readyState==4 && xmlhttp.status==200)
            {
                var str=xmlhttp.responseText;
                if(str=="wrong"){
                    obj.value="";
                }
                if(obj.value=="") {
                    document.getElementById("button").innerText="无效邀请码";
                    document.getElementById("button").className = "btn btn-default btn-block disabled btn-danger";
                }
                else {
                    document.getElementById("button").innerText="提交邀请码";
                    document.getElementById("button").className = "btn btn-block btn-primary btn-default";
                    document.getElementById("button").disabled="";
                }
            }
        }
    }
</script>
</body>
</html>
