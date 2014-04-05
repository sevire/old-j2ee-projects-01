<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="co.uk.genonline.simpleweb.controller.RequestStatus" %>
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
    <link rel="stylesheet" href="css/main.css" type="text/css" media="all" >
    <link rel="icon" type="image/png" href="favicon.png">
</head>
<body id='editIndex'>
<div id='colLeft'></div>
<div id='colRight'></div>
<div id='colMiddle'>
    <h1>Lucifer's Dark Angel - Website Maintenance Screen</h1>
    <div>
        <table>
            <tr class='statusRow <%=((RequestStatus)session.getAttribute("requestStatus")).getStatusType()%>'>
                <td colspan="11"><%=((RequestStatus)session.getAttribute("requestStatus")).getStatusMessage()%></td>
            </tr>
            <tr class='tableHeading'>
                <td>
                    Name
                </td>
                <td>
                    Sort Key
                </td>
                <td>
                    Short Title
                </td>
                <td>
                    Long Title
                </td>
                <td>
                    Screen Type
                </td>
                <td>
                    Screen  Display Type
                </td>
                <td>
                    Enabled?
                </td>
                <td>
                    Gallery?
                </td>
                <td>
                    Page Content
                </td>
            </tr>
            <c:forEach var="row" items="${editList}">
                <tr>
                    <td>
                        <c:out value="${row.name}" />
                    </td>
                    <td>
                        <c:out value="${row.sortKey}" />
                    </td>
                    <td>
                        <c:out value="${row.screenTitleShort}" />
                    </td>
                    <td>
                        <c:out value="${row.screenTitleLong}" />
                    </td>
                    <td>
                        <c:out value="${row.screenType}" />
                    </td>
                    <td>
                        <c:out value="${row.screenDisplayType}" />
                    </td>
                    <td>
                        <c:out value="${row.enabledFlag ? 'Yes' : 'No'}" />
                    </td>
                    <td>
                        <c:out value="${row.galleryFlag ? 'Yes' : 'No'}" />
                    </td>
                    <td>
                        <c:out value="${row.screenContents}" />
                    </td>
                    <td>
                        <a href="edit?screen=${row.name}">Edit</a>
                    </td>
<%--
                    <td>
                        <a href="delete?screen=${row.name}">Delete</a>
                    </td>
--%>
                    <td>
                        <a href="view?screen=${row.name}">View</a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
            <td colspan="8"></td>
                <td>
                    <a href="add">Add</a>
                 </td>
            </tr>
        </table>
    </div>
</div>
</body>
</html>