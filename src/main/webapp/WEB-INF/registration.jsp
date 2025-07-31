<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 79056
  Date: 02.05.2025
  Time: 21:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Registration</title>
    <style>
        .input {}
        .input::placeholder {
            color: #999;
        }

        .input:focus::placeholder {
            color: transparent;
        }
    </style>
</head>
<body>
<form action="${pageContext.request.contextPath}/registration" method="POST">
    <label>
        <input type="text" class="input" placeholder="Enter login" name="login" >
    </label>
    <label>
        <input type="password" class="input" placeholder="Enter login" name="password" >
    </label>
    <button type="submit">Register</button>
</form>
<c:if test='${requestScope.get("message")!=null}'>
    <c:out value='${requestScope.get("message")}'/>
</c:if>
<form action="${pageContext.request.contextPath}/login" method="get">
    <button type="submit">Login</button>
</form>
</body>
</html>
