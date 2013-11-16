<%@ page import="co.uk.genonline.simpleweb.controller.RequestStatus" %>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 13/05/2012
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Lucifer's Dark Angel - Edit Configuration Index</title>
    <link rel="stylesheet" href="css/main.css" type="text/css" media="all" >
    <link rel="icon" type="image/png" href="favicon.png">
</head>
<body id='editConfigIndex'>
<div id='colLeft'></div>
<div id='colRight'></div>
<div id='colMiddle'>
    <h1>Lucifer's Dark Angel - Website Maintenance Screen</h1>
    <div>
        <table>
            <tr class='statusRow <%=((RequestStatus)session.getAttribute("requestStatus")).getStatusType()%>'>
                <td colspan="9"><%=((RequestStatus)session.getAttribute("requestStatus")).getStatusMessage()%></td>
            </tr>
            <tr class='tableHeading'>
                <td>
                    Name
                </td>
                <td>
                    Value
                </td>
            </tr>
            <core:forEach var="row" items="${configItems}">
                <tr>
                    <td>
                        <core:out value="${row.name}" />
                    </td>
                    <td>
                        <core:out value="${row.value}" />
                    </td>
                    <td>
                        <a href="editConfigItem?name=${row.name}">Edit</a>
                    </td>
                    <td>
                        <a href="deleteConfigItem?name=${row.name}">Delete</a>
                    </td>
                </tr>
            </core:forEach>
            <tr>
            <td colspan="8"></td>
                <td>
                    <a href="addConfigItem">Add</a>
                 </td>
            </tr>
            <tr>
                <td colspan="8"></td>
                <td>
                    <a href="reloadConfiguration">Reload Configuration</a>
                </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>