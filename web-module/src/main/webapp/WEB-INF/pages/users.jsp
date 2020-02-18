<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page import="com.gmail.yauhen2012.controller.constant.JSPConstant" %>
<html>
<head>
    <title>Users</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col">
            <table class="table table-bordered table-striped">
                <thead class="thead-dark">
                <tr>
                    <td>ID</td>
                    <td>Username</td>
                    <td>Role</td>
                    <td>Action</td>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="user" items="${users}">
                    <tr>
                        <td><c:out value="${user.id}"/></td>
                        <td><c:out value="${user.userName}"/></td>
                        <td><c:out value="${user.role}"/></td>
                        <td>
                        <p><a class="addLink" href='<c:url value="/user?id=${user.id}" />'>Details</a></p>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <c:if test="${sessionScope.userRole == JSPConstant.ADMIN_ROLE}">
                   <p><a class="addLink" href='<c:url value="/users/new" />'>Add new user</a></p>
            </c:if>
            <p><a class="addLink" href='<c:url value="/logout" />'>Logout</a></p>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/pages/js.jsp" %>
</body>
</html>