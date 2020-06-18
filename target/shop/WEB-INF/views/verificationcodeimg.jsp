<%--
  Created by IntelliJ IDEA.
  User: Gary
  Date: 2020/3/16
  Time: 14:38
  To change this template use File | Settings | File Templates.
--%>
<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page contentType="image/jpeg"%>
<jsp:useBean id="image" scope="page" class="com.wgy.shop.utils.Verificate" />
<%
    String str = image.getCertPic(0, 0, response.getOutputStream());
    // 将认证码存入session中
    session.setAttribute("certCode", str);
    out.clear();
    out = pageContext.pushBody();
%>
<html>
<head>
    <title>验证码</title>
</head>
<body>

</body>
</html>
