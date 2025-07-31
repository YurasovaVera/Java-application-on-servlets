<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: 79056
  Date: 03.05.2025
  Time: 12:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Employee</title>
    <style>
        table { border-collapse: collapse; width: 100%; margin-top: 20px; }
        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }
        .error { color: red; padding: 5px; margin: 5px 0; border: 1px solid red; }
        .form-container { margin-top: 10px; padding: 15px; background: #f5f5f5; }
        .form-group { margin-bottom: 10px; }
        .form-group label { display: inline-block; width: 80px; }
        .header-container { display: flex; justify-content: space-between; align-items: center;}
        .action-buttons { display: flex; gap: 5px; }
    </style>
</head>
<body>
<c:if test="${not empty error}">
    <div class="error"><c:out value=" ${error}"/></div>
</c:if>
<div class="search-container">
    <form action="${pageContext.request.contextPath}/user/employee" method="post">
        <input type="hidden" name="action" value="search">
        <input type="text" name="searchName" placeholder="Search by name..."
               value="${fn:escapeXml(param.searchName)}">
        <button type="submit">Search</button>
        <c:if test="${not empty param.searchName}">
            <a href="${pageContext.request.contextPath}/user/employee">
                <button type="button">Show All</button>
            </a>
        </c:if>
    </form>
</div>
<div class="header-container">
    <h2>Employees</h2>
    <c:choose>
        <c:when test="${empty param.showAddForm and empty editEmployee}">
            <a href="?showAddForm=true">
                <button>Add Employee</button>
            </a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/user/employee">
                <button>Close Form</button>
            </a>
        </c:otherwise>
    </c:choose>
</div>

<!-- Форма для добавления -->
<c:if test="${not empty param.showAddForm and empty editEmployee}">
    <div class="form-container">
        <form action="${pageContext.request.contextPath}/user/employee" method="post">
            <input type="hidden" name="action" value="insert">
            <input type="hidden" name="id_profile" value="${sessionScope.userId}">

            <div class="form-group">
                <label>Name:</label>
                <input type="text" name="name" required>
            </div>

            <div class="form-group">
                <label>Age:</label>
                <input type="number" name="age" required>
            </div>

                <%--<div class="form-group">
                    <label>Profile id:</label>
                    <c:out value="${sessionScope.userId}"/>
                </div>--%>

                <div class="action-buttons">
                    <button type="submit">Save</button>
                    <a href="${fn:escapeXml(pageContext.request.contextPath)}/user/employee">
                        <button type="button">Cancel</button>
                    </a>
                </div>
            </form>
        </div>
    </c:if>
    <table>
        <thead>
        <tr>
            <th>ID</th>
            <th>Name</th>
            <th>Age</th>
            <th>Profile id</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${employees}" var="employee">
            <tr>
                <td><c:out value="${employee.id}"/></td>
                <td><c:out value="${employee.name}"/></td>
                <td><c:out value="${employee.age}"/></td>
                <td><c:out value="${employee.id_profile}"/></td>
                <td>
                    <!-- Форма для удаления -->
                    <form action="${fn:escapeXml(pageContext.request.contextPath)}/user/employee" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${employee.id}">
                        <button type="submit">Delete</button>
                    </form>

                    <!-- Форма для редактирования -->
                    <form action="${fn:escapeXml(pageContext.request.contextPath)}/user/employee" method="post" style="display: inline;">
                        <input type="hidden" name="action" value="edit">
                        <input type="hidden" name="id" value="${employee.id}">
                        <button type="submit">Edit</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <!-- Форма для редактирования -->
    <c:if test="${not empty editEmployee}">
    <div class="form-container">
        <h3>Edit Employee</h3>
        <form action="${fn:escapeXml(pageContext.request.contextPath)}/user/employee" method="post">
            <input type="hidden" name="action" value="update">
            <input type="hidden" name="id" value="<c:out value='${editEmployee.id}'/>">
            <input type="hidden" name="id_profile" value="<c:out value='${editEmployee.id_profile}'/>">

            <div class="form-group">
                <label for="editName">Name:</label>
                <input type="text" id="editName" name="name" value="<c:out value='${editEmployee.name}'/>" required>
            </div>

            <div class="form-group">
                <label for="editAge">Age:</label>
                <input type="number" id="editAge" name="age" value="<c:out value='${editEmployee.age}'/>" required>
            </div>

        <%--<div class="form-group">
            <label>Profile ID:</label>
            <div class="readonly-field"><c:out value="${editEmployee.id_profile}"/></div>
        </div>--%>

    <div class="action-buttons">
        <button type="submit">Save</button>
        <a href="${fn:escapeXml(pageContext.request.contextPath)}/user/employee">
            <button type="button">Cancel</button>

        </a>
    </div>
</form>
</c:if>
<!-- Форма для добавления -->
<%--<c:if test="${empty editEmployee}">
<h2>Add employee</h2>
<form action="${pageContext.request.contextPath}/user/employee" method="post">
    <input type="hidden" name="action" value="insert">

    <div class="form-group">
        <label>Name:</label>
        <input type="text" name="name" value="" required>
    </div>

    <div class="form-group">
        <label>Age:</label>
        <input type="number" name="age" value="" required>
    </div>

    <div class="form-group">
        <label>ID profile:</label>
        <input type="number" name="id_profile" value="" required>
    </div>
    <button type="submit">Add</button>
</form>
</c:if>--%>

    <form action="${fn:escapeXml(pageContext.request.contextPath)}/logout" method="get">
    <button type="submit">Logout</button>
</form>
</body>
</html>
