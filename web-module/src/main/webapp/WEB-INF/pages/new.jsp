<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>addNewUser</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<h2>User registration</h2>
<br><br>
<form method="post">
    <label>Name</label><br>
        <input name="username" type="text" pattern="[a-zA-Z]+" placeholder="Only English letters" maxlength="16" minlength="3" required/>
        <br><br>

    <label>Password</label><br>
        <input name="password" type="text" pattern="(?=.*[0-9])(?=.*[a-zA-Z])\w{3,}" placeholder="Three or more characters" maxlength="16" minlength="3" required/>
        <br><br>

    <label>First name</label><br>
          <input name="firstName" type="text" pattern="[a-zA-Z]+" placeholder="Only English letters" maxlength="16" required/>
          <br><br>

    <label>Last name</label><br>
          <input name="lastName" type="text" pattern="[a-zA-Z]+" placeholder="Only English letters" maxlength="16" required/>
          <br><br>

    <label>Role</label><br>
          <input type="radio" name="role" value="ADMIN" checked/> Admin<br>
          <input type="radio" name="role" value="USER"/> User<br>
          <br><br>

    <label>Status</label><br>
          <input type="radio" name="isActive" value="true" checked/> ACTIVE<br>
          <input type="radio" name="isActive" value="false"/> INACTIVE<br>
          <br><br>
    <input type="submit" value="registration" class="button">
</form>
</div>

<%@include file="/WEB-INF/pages/js.jsp" %>
</body>
</html>