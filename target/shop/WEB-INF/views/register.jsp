<%--
  Created by IntelliJ IDEA.
  User: Gary
  Date: 2020/3/16
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>淘一淘-个人注册</title>
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap/css/bootstrap.min.css">
    <script src="${pageContext.request.contextPath}/css/bootstrap/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/holder.js"></script>
    <script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/sweetalert.min.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/sweetalert.css">
</head>
<body>
    <div id="main" class="container">
        <%--调用头部jsp--%>
        <div id="header">
            <%@ include file="header.jsp"%>
        </div>

        <div class="login">
            <div class="row">
                <div class="col-md-6">
                    <img src="${pageContext.request.contextPath}/image/register.png" width="450" height="600" alt="" style="margin-left: 40px;">
                </div>
                <div class="col-md-5 form-register">
                    <div>
                        <%--提交给控制器--%>
                        <form class="form-horizontal" id="form" action="" method="post">
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="username" name="username" placeholder="昵称">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="password" name="password" placeholder="密码">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="确认密码">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="email" name="email" placeholder="邮箱">
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-7">
                                    <input type="text" class="form-control" id="emailcode" name="emailcode" placeholder="邮箱校验">
                                </div>
                                    <input type="button" class="btn btn-primary" value="发送验证码" id="email_confirm">
                            </div>
                            <div class="form-group">
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="telephone" name="telephone" placeholder="联系方式">
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-sm-10">
                                    <input type="hidden" name="flag" id="flag" value="1">
                                    <input type="submit" class="btn btn-primary form-control" value="注册" id="register_btn">
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
<script type="text/javascript">
    //设置时间
    var wait=60;
    function time(o) {
        if (wait == 0) {
            o.removeAttribute("disabled");
            o.value="发送验证码";
            wait = 60;
        } else {
            o.setAttribute("disabled", true);
            o.value="重新发送(" + wait + ")";
            wait--;
            setTimeout(function() {
                    time(o)
                },
                1000)
        }
    }
   // 前端校验
    $(function () {
        //实时监控,因为输出的不一样就不封装函数了
        $("#username").change(function () {
            var username = $("#username").val();
            var regName = /(^[a-zA-Z0-9_-]{3,16}$)|(^[\u2e80-\u9FFF]{2,5})/;
            if(!regName.test(username)){
                $("#errorMsg").text("用户名可以是2-5位中文或者3-16位英文和数字的组合").css({"color":"red"});
            }else{
                $("#errorMsg").text(" ");
            }
        });
        $("#password").change(function () {
            var password = $("#password").val();
            var regName = /^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{8,16}$/;
            if(!regName.test(password)){
                $("#errorMsg").text("密码由数字和字母组成，并且要同时含有数字和字母，且长度要在6-16位之间").css({"color":"red"});
            }else{
                $("#errorMsg").text(" ");
            }
        });
        $("#email").change(function () {
            var email = $("#email").val();
            var regName = /^([a-zA-Z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
            if(!regName.test(email)){
                $("#errorMsg").text("邮箱格式不正确").css({"color":"red"});
            }else{
                $("#errorMsg").text(" ");
            }
        });
        $("#telephone").change(function () {
            var telephone = $("#telephone").val();
            var regName = /^((13\d{9}$)|(15[0,1,2,3,5,6,7,8,9]\d{8}$)|(18[0,2,5,6,7,8,9]\d{8}$)|(147\d{8})$)/;
            if(!regName.test(telephone)){
                $("#errorMsg").text("手机号码格式不正确").css({"color":"red"});
            }else{
                $("#errorMsg").text(" ");
            }
        });
    });

    $(function () {
        //点击注册按钮时发送请求
        $("#register_btn").click(function () {
            //因为按钮存在在表单中，所以点击按钮的时候需要阻止表单的默认行为
            window.event.returnValue = false;
            //发送ajax请求
            $.ajax({
                url: "/shop/registerresult",
                method: "POST",
                data: {
                    "username": $("#username").val(),
                    "password": $("#password").val(),
                    "confirmPassword" :$("#confirmPassword").val(),
                    "email" :$("#email").val(),
                    "emailcode" :$("#emailcode").val(),
                    "telephone" :$("#telephone").val()
                },
                success: function(response){
                    console.log(response.errorCode);
                    if(response.errorCode === "100"){
                        swal("恭喜，账号注册成功，请登录吧！");
                        window.location = "/shop/login";
                    }else {
                        $("#errorMsg").text(response.objectMap.msg).css({"color":"red"});
                    }
                },
                error: function () {
                    /*$("#errorMsg").text("请求迷路了，小二正在赶来的路上，请稍后再试..").css({"color":"red"});*/
                    swal("请求迷路了，小二正在赶来的路上，请稍后再试..");
                    window.location = "/shop/register";
                }
            });
        });
    });

    $(function () {
        $("#email_confirm").click(function () {
            console.log("点击成功");
            //发送ajax请求
            $.ajax({
                url: "/shop/registerconfirm",
                method: "POST",
                data: {
                    "username": $("#username").val(),
                    "email" :$("#email").val()
                },
                success: function (response) {
                    if(response.errorCode === "100"){
                        //发送成功
                        swal("验证码发送成功，请耐心等待！", "", "success");
                    }else{
                        //发送失败
                        swal(response.objectMap.msg+"发送失败，请稍后再试","","error");
                    }
                },
                error: function () {
                    swal("发送验证码请求失败","","error");
                }
            });
            time(this);
        });
    });
</script>
