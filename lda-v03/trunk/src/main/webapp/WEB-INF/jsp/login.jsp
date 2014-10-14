<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 17/09/2014
  Time: 07:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<form:form action="login" method="post"  modelAttribute="loginAttribute">
    <table border="0" cellpadding="0" cellspacing="0">
        <tr>
            <th>Username</th>
            <td><form:input type="text"  class="login-inp" path="username" /></td>
        </tr>
        <tr>
            <th>Password</th>
            <td><form:input type="password" value="************"  onfocus="this.value=''" class="login-inp" path="password"/></td>
        </tr>
        <tr>
            <th></th>
            <td valign="top"><input type="checkbox" class="checkbox-size" id="login-check" /><label for="login-check">Remember me</label></td>
        </tr>
        <tr>
            <th></th>
            <td><input type="submit" class="submit-login"  /></td>
        </tr>
    </table>
</form:form>
</body>
</html>
