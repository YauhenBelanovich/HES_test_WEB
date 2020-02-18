<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>editUser</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css">
</head>
<body>
<div class="container">
<h2>Edit User</h2>
<br><br>
<form method="post">
    <label>New First Name</label><br>
        <input name="newFirstName" type="text" pattern="[a-zA-Z]+" placeholder="Only English letters" maxlength="16"/>
        <br><br>


    <label>New Last Name</label><br>
          <input name="newLastName" type="text" pattern="[a-zA-Z]+" placeholder="Only English letters" maxlength="16"/>
          <br><br>

    <input type="submit" value="Submit" class="button">
</form>
</div>

<%@include file="/WEB-INF/pages/js.jsp" %>
</body>
</html>