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
    <title>Lucifer's Dark Angel - Edit : <%out.print((String)request.getAttribute("screenTitleLong"));%></title>
    <link rel="stylesheet" href="./css/main.css" type="text/css" media="all" >
</head>
<body id='Edit'>
<div id='colLeft'></div>
<div id='colRight'></div>
<div id="'colMiddle">
    <div id="editIndex">
        <core:forEach var="row" items="${editList}">
            <p><a href="edit?screen=${row.name}"><core:out value="${row.screenTitleLong}" /></a></p>
        </core:forEach>
    </div>
</div>
</body>
</html>