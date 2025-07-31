<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 79056
  Date: 03.05.2025
  Time: 12:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Welcome</title>
</head>
<body>
<p>Welcome ${userName}</p>
<form action="${pageContext.request.contextPath}/user/employee" method="get">
    <button type="submit">Go to employee</button>
</form>
<form action="${pageContext.request.contextPath}/logout" method="get">
    <button type="submit">Logout</button>
</form>
</body>
</html>
