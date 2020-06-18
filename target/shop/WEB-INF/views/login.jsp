<%--
  Created by IntelliJ IDEA.
  User: Gary
  Date: 2020/3/16
  Time: 12:17
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>淘一淘-请登录</title>
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/css/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/holder.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/login.js"></script>
    <script src="${pageContext.request.contextPath}/js/sweetalert.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sweetalert.css">
</head>
<script type="text/javascript">
    //生成验证码
    function reloadcode() {
        var verify = document.getElementById('code');
        verify.setAttribute("src", "${pageContext.request.contextPath}/verificationcodeimg?it=" + Math.random());
        $("#errorMsg").text("");
    }
</script>
<body>
    <div id="main" class="container">
        <div id="header">
            <%@ include file="header.jsp"%>
        </div>
        <div class="login">
            <div class="row">

                <%--放置图片--%>
                <div class="col-md-6">
                    <img src="./image/login.png" alt="" width="640" height="400" style="margin-left:-40px;margin-top:30px;">
                </div>
                <div class="col-md-5 form-login">
                    <div style="font-weight:bold;color:#000000;font-size: 20px">
                        <span>密码登录</span>
                    </div>
                    <div style="margin-top: 10px">
                        <%--提交到loginconfirm去验证信息--%>
                        <form class="form-horizontal" id="form2" action="" method="post">
                            <div class="form-group">
                                <label for="username" class="col-sm-2 control-label">用户名</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="username" name="username" placeholder="用户名">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="password" class="col-sm-2 control-label">密码</label>
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="password" name="password" placeholder="密码">
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="confirmlogo" class="col-sm-2 control-label">验证码</label>
                                <%--点击图片更换验证码--%>
                                <img src="${pageContext.request.contextPath}/verificationcodeimg" id="code" onclick="reloadcode()"
                                     style="cursor: pointer;" alt="看不清楚,换一张" width="100px">
                                <div class="col-sm-10" style="width: 290px">
                                    <input type="text" class="form-control" id="confirmlogo" name="confirmlogo" placeholder="验证码">
                                </div>
                            </div>
                            <div style="margin-left:80px;color:red;">
                            </div>

                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <input type="submit" class="btn btn-info btn-lg btn-block login-input" value="登录" name="submit" id="login_btn">
                                    <a href="${pageContext.request.contextPath}/register" style="margin-left:240px;">免费注册</a>
                                    <%--待完善--%>
                                    <a href="" style="margin-left:20px;">忘记密码</a>
                                    <div class="info" id="errorMsg"></div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <div style="width: 100%; position: fixed; bottom: 0px; text-align: center">
        Copyright&nbsp;©&nbsp;2020-2020&nbsp;&nbsp;淘一淘&nbsp;版权所有
    </div>
</body>
</html>
