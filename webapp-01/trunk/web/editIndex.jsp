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
    <title>Lucifer's Dark Angel - Edit Index</title>
    <link rel="stylesheet" href="./css/main.css" type="text/css" media="all" >
</head>
<body id='editIndex'>
<div id='colLeft'></div>
<div id='colRight'></div>
<div id='colMiddle'>
    <div>
        <table>
            <tr class='tableHeading'>
                <td>
                    Name
                </td>
                <td>
                    Enabled?
                </td>
                <td>
                    Short Title
                </td>
                <td>
                    Long Title
                </td>
                <td>
                    Text
                </td>
            </tr>
            <core:forEach var="row" items="${editList}">
                <tr>
                    <td>
                        <core:out value="${row.name}" />
                    </td>
                    <td>
                        <core:out value="${row.enabledFlag ? 'Yes' : 'No'}" />
                    </td>
                    <td>
                        <core:out value="${row.screenTitleShort}" />
                    </td>
                    <td>
                        <core:out value="${row.screenTitleLong}" />
                    </td>
                    <td>
                        <core:out value="${row.screenContents}" />
                    </td>
                    <td>
                        <a href="edit?screen=${row.name}">Edit</a>
                    </td>
                    <td>
                        <a href="delete?screen=${row.name}">Delete</a>
                    </td>
                    <td>
                        <a href="view?screen=${row.name}">View</a>
                    </td>
                </tr>
            </core:forEach>
            <tr>
            <td colspan="5"></td>
                <td>
                    <a href="add">Add</a>
                 </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>