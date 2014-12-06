<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 20/07/2014
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${testimonialData.linkData.longName}</title>
    <link rel="stylesheet" href="/css/reset.css" type="text/css" media="all">
    <link rel="stylesheet" href="/css/general.css" type="text/css" media="all">
</head>
<body id="${testimonialData.linkData.name}">
<div id="container">
    <div id="topNav">
        ${topLinkbar}
    </div>
    <div id="sidebar1">
        ${siblingLinkbar}
    </div>
    <div id="content1">
        <h1>${testimonialData.linkData.longName}</h1>
        ${testimonialData.testimonialContentDecoded}
    </div>
</div>


</body>
</html>