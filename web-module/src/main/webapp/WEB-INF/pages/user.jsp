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
                            <td>Name</td>
                            <td>Role</td>
                            <td>Create Date</td>
                            <td>First Name</td>
                            <td>Last Name</td>
                            <td>Status</td>
                            <c:if test="${sessionScope.userRole == JSPConstant.ADMIN_ROLE}">
                            <td>Action</td>
                            <td>Action</td>
                            </c:if>
                        </tr>
                        </thead>
                        <tbody>
                            <tr>
                                <td><c:out value="${userById.id}"/></td>
                                <td><c:out value="${userById.userName}"/></td>
                                <td><c:out value="${userById.role}"/></td>
                                <td><c:out value="${userById.createdBy}"/></td>
                                <td><c:out value="${userById.firstName}"/></td>
                                <td><c:out value="${userById.lastName}"/></td>
                                <td>
                                <c:out value="${userById.active? JSPConstant.TRUE_VALUE:JSPConstant.FALSE_VALUE}"/>

                                <c:if test="${sessionScope.userRole == JSPConstant.ADMIN_ROLE}">
                                   <form method="post" action='<c:url value="/changeStatus" />' style="display:inline;">
                                            <input type="hidden" name="id" value="${userById.id}">
                                            <input type="hidden" name="active" value="${userById.active}">
                                            <input type="submit" value="Change" class="button">
                                   </form>
                                </c:if>
                                </td>
                                <c:if test="${sessionScope.userRole == JSPConstant.ADMIN_ROLE}">
                                <td>
                                <form method="post" action='<c:url value="/deleteUser" />' style="display:inline;">
                                       <input type="hidden" name="id" value="${userById.id}">
                                       <input type="submit" value="Delete" class="button">
                                </form>
                                </td>
                                <td>
                                <p><a class="addLink" href='<c:url value="/user/edit?id=${userById.id}" />'>Edit</a></p>
                                </td>
                                </c:if>
                            </tr>
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