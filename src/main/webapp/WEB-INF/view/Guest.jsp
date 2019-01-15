<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 10390
  Date: 2018/7/26
  Time: 16:01
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
                    非常感谢您使用本程序！你的填写会帮助任务创建者节省许多精力！<br>
                    <strong>不过你的填写有一点非常重要<br>
                        一旦涉及到时间的格式填写无论任务要求你是什么格式，一律精确到年月日，并按照（2000/1/1）这个格式，年月日之间一点用斜杠隔开<br></strong>
                    剩下的程序会为你完成格式转换<br>
                    不按照上述格式要求会导致提交失败<br>
                    谢谢配合！
                </p>
            </div>
        </div>
        <div class="col-md-6 column">
            <div class="page-header">
                <h1>
                    Example page header <small>Subtext for header</small>
                </h1>
            </div>
            <form role="form" action="/Nexcel/guest/add" method="post">
                <c:forEach items="${requestScope.model}" var="item">
                <div class="form-group">
                    <label>${item}</label><input class="form-control"  type="Text" name="text" />
                </div>
                </c:forEach>
                <button type="submit" class="btn btn-default">Submit</button>
            </form>
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