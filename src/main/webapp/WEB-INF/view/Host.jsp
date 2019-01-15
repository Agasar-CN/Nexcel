<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 10390
  Date: 2018/7/24
  Time: 16:40
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
                    <strong>感谢您的注册，阅读以下消息可以让你正确的发出任务邀请。</strong><br>
                    如果你想要发起一个任务的话，你只需上传一个Excel文件，<strong>对于上传的文件有严格要求!</strong><br>
                    你要上传的Excel只能有两行<br>
                    第一行应当含有你需要对方填写的内容的名称。（如：姓名 年龄 出生年月日）
                    第二行应当含有你希望参与者如何填写的一个模板（如：Nobody 18 2000年1月1日)这个模板旨在确立你所设定的字体大小，字体颜色，时间格式。<br>
                    <strong>请严格按照上述要求填写文件,否则可能会出现未知错误，导致任务失败！</strong> 谢谢您的配合！希望这个程序可以让你工作更加方便。
                </p>
            </div>
        </div>
        <div class="col-md-6 column">
            <div class="alert alert-dismissable alert-info">
                <button type="button" class="close" data-dismiss="alert" aria-hidden="true">×</button>
                <h4 >
                    注意！
                </h4>
                <strong id="str">${requestScope.info}</strong>
                <h5>如果想要发起一个任务邀请，请上传你的模板文件！</h5>
            </div>
            <form role="form" action="/Nexcel/user/upload" method="post" enctype="multipart/form-data">
                <div class="form-group">
                    <label for="exampleInputFile">File input</label><input id="exampleInputFile" type="file" name="file" />
                </div>
                <button type="submit" class="btn btn-default btn-primary">上传文件</button>
            </form>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">
                        下列是你已经发布的任务
                    </h3>
                </div>
                <div class="panel-body">
                    <c:forEach items="${requestScope.posts}" var="item" >
                        <blockquote>
                            <p>
                                <a href="/Nexcel/user/down?code=${item.code}">${item.postname}</a>
                            </p> <small>邀请码：${item.code}<cite>&nbsp;&nbsp;已填写人数：${item.num} </cite></small>
                        </blockquote>
                    </c:forEach>
                </div>
                <div class="panel-footer">
                    你随时可以点击文件名下载
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
</body>
</html>
