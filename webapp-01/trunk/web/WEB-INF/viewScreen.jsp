<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 14/05/2012
  Time: 07:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>View Screen</title>
</head>
<body>
    <div id='viewScreen'>
        <label>Screen Name:</label>
        <p><%out.print(request.getAttribute("screenName"));%></p>
        <label>Screen Title Short:</label>
        <p><%out.print(request.getAttribute("screenTitleShort"));%></p>
        <label>Screen Title Long:</label>
        <p><%out.print(request.getAttribute("screenTitleLong"));%></p>
        <label>Screen Contents:</label>
        <div id='screenContents'><%out.print(request.getAttribute("screenContents"));%></div>
        <form action="Controller.do">
            <input type='submit' name='confirmButton' value='Confirm'>
            <input type='submit' name='editButton' value='Edit'>
            <input type='submit' name='cancelButton' value='Cancel'>
        </form>
    </div>

</body>
</html>