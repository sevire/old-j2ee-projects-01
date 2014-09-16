<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 15/09/2014
  Time: 19:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title></title>
</head>
<body>
<form:form commandName="mistressEntity" action="mistressadd">

    <table>
        <tr>
            <td>Screen Name:</td>
            <td><form:input path="mistressName" /></td>
        </tr>
        <tr>
            <td>Short Display Name (for links):</td>
            <td><form:input path="mistressShortName" /></td>
        </tr>
        <tr>
            <td>Long Display Name (for Page Headings - can be blank):</td>
            <td><form:input path="mistressLongName" /></td>
        </tr>
        <tr>
            <td>Page Content</td>
            <td><form:input path="mistressContent" /></td>
        </tr>
        <tr>
            <td>Page Content Type</td>
            <td><form:input path="mistressContentType" /></td>
        </tr>
        <tr>
            <td colspan="2">
                <input type="submit" value="Save Changes" />
            </td>
        </tr>
    </table>
</form:form>
</body>
</html>
