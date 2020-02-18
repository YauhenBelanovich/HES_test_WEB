<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col">
            <c:if test="${param.is_not_valid}">
                Login or password is not valid
            </c:if>
            <form method="post" action="${pageContext.request.contextPath}/login">
                <div class="form-group">
                    <label for="exampleInputUsername">Username</label>
                    <input type="text" name="username" class="form-control" id="exampleInputUsername" placeholder="Enter username">
                </div>
                <div class="form-group">
                    <label for="exampleInputPassword1">Password</label>
                    <input type="password" name="password" class="form-control" id="exampleInputPassword1" placeholder="Password">
                </div>
                <button type="submit" class="btn btn-primary">Submit</button>
            </form>
        </div>
    </div>
</div>

<%@include file="/WEB-INF/pages/js.jsp" %>
</body>
</html>