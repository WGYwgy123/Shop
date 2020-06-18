<%--
  Created by IntelliJ IDEA.
  User: Gary
  Date: 2020/3/16
  Time: 11:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/font-awesome.min.css">
<%--<script type="text/javascript">
    $(function () {
        $("#cart_href").click(function () {
            window.event.returnValue = false;
            //发送ajax请求
            $.ajax({
                url: "/shop/showcart",
                type: "POST",
                success: function (response) {
                    if(response.objectMap.msg === "100"){
                        window.location = "/shop/shopcart";
                    }
                    else{
                        alert(response.objectMap.msg);
                        window.location = "/shop/login";
                        return false;
                    }
                },
                error: function () {
                    /*$("#errorMsg").text("请求迷路了，小二正在赶来的路上，请稍后再试..").css({"color":"red"});*/
                    alert("请求迷路了，小二正在赶来的路上，请稍后再试..");
                    window.location = "/shop/login";
                }
            });
        });
    });
</script>--%>
<%--制作顶部的跳转链接--%>
<div class="container-fluid" style="background-color:#F5F5F5; width: 100%">
    <div class="col-md-4" role="navigation">
        <ul class="nav nav-pills">
            <%--判断如果没登录则跳转到login页面--%>
            <c:if test="${empty sessionScope.user}">
                <li>
                    <a href="${pageContext.request.contextPath}/login" style="color: #F22E00">亲，请登录</a>
                </li>
            </c:if>
            <%--如果登录则获取名字并跳转到infomation界面--%>
            <c:if test="${!empty sessionScope.user}">
                <li class="info-a">
                    <a href="${pageContext.request.contextPath}/information" style="color: #F22E00">
                        ${sessionScope.user.username}
                        <span class="glyphicon glyphicon-triangle-bottom" style="font-size: 5px;margin-left: 7px;" aria-hidden="true"/>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="${pageContext.request.contextPath}/information">账户管理</a></li>
                        <li role="separator" class="divider"></li>
                        <li><a href="${pageContext.request.contextPath}/logout" class="login-out">退出登录</a></li>
                    </ul>
                </li>
            </c:if>

            <%--跳转到注册界面--%>
            <li><a href="${pageContext.request.contextPath}/register" style="color: #6C6C6C">免费注册</a></li>
        </ul>
    </div>

    <div class="col-md-8">
        <ul class="nav nav-pills pull-right">
            <%--跳转到chat界面--%>
            <li>
                <a href="${pageContext.request.contextPath}/chat" style="color: #6C6C6C">
                    <i class="fa fa-comment"></i> 消息</a>
            </li>
            <%--跳转到showcart界面--%>
            <li>
                <a href="${pageContext.request.contextPath}/showcart" style="color: #6C6C6C" id="cart_href">
                <i class="fa fa-shopping-cart" style="color: #F22E00"></i> 购物车</a>
            </li>
            <%--跳转到favorite界面--%>
            <li>
                <a href="${pageContext.request.contextPath}/info/favorite" style="color: #6C6C6C">
                    <i class="fa fa-star"></i> 收藏夹</a>
            </li>
        </ul>
    </div>
</div>
<%--制作导航栏--%>
<div id="header-nav">
    <nav class="navbar navbar-default" id="header-nav-middle">
        <div class="container-fluid">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                        data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                    <span class="sr-only">Toggle navigation</span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <%--点击淘身边跳转回main界面--%>
                <a class="navbar-brand" href="${pageContext.request.contextPath}/main">
                    <span class="logo-word">淘身边</span>
                </a>
            </div>

            <%--导航栏的超链接以及下拉框--%>
            <div class="collapse navbar-collapse"
                 id="bs-example-navbar-collapse-1">
                <ul class="nav navbar-nav">
                    <li>
                        <a class="a-color" href="${pageContext.request.contextPath}/main">首页</a>
                    </li>
                    <li>
                        <a class="a-color" href="${pageContext.request.contextPath}/information">个人信息</a>
                    </li>
                    <li class="dropdown">
                        <a class="a-color" href="${pageContext.request.contextPath}/info/list" class="dropdown-toggle"
                           data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">我的订单
                            <span class="caret"></span></a>
                        <ul class="dropdown-menu">
                            <li>
                                <a href="${pageContext.request.contextPath}/info/address">地址管理</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/order">交易中</a>
                            </li>
                            <li role="separator" class="divider"></li>
                            <li>
                                <a href="${pageContext.request.contextPath}/chatrobot">小淘</a>
                            </li>
                        </ul>
                    </li>
                </ul>

                <%--搜索框--%>
                <form class="navbar-form navbar-right" role="search" method="get" action="${pageContext.request.contextPath}/search">
                    <div class="form-group">
                        <input type="text" class="form-control" placeholder="Search" name="keyword">
                    </div>
                    <button type="submit" class="btn btn-default">
                        <span class="glyphicon glyphicon-search" aria-label="搜索"></span>
                    </button>
                </form>
            </div>
        </div>
    </nav>
</div>