<%--
  Created by IntelliJ IDEA.
  User: 79056
  Date: 02.05.2025
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Login</title>
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
<c:if test="${not empty error}">
    <div class="error-message">
            ${error}
    </div>
</c:if>
<c:if test="${not empty sessionScope.registrationSuccess}">
    <div class="alert alert-success">Registration successful!</div>
    ${sessionScope.remove("registrationSuccess")}
</c:if>
<form method="POST" action="${pageContext.request.contextPath}/login">
    <input type="text" class="input" placeholder="Enter login" name="login" >
    <input type="password" class="input" placeholder="Enter password" name="password" >
    <button type="submit">do login</button>
</form>
<form action="${pageContext.request.contextPath}/registration" method="get">
    <button type="submit">Create account</button>
</form>
</body>
</html>
