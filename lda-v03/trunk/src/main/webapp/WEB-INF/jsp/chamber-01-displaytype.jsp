<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 20/07/2014
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="data" scope="request" type="uk.co.genonline.ldav03.model.chamber.ChamberInformation"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${data.linkData.longName}</title>
    <link rel="stylesheet" href="/css/reset.css" type="text/css" media="all">
    <link rel="stylesheet" href="/css/general.css" type="text/css" media="all">
</head>
<body id="${data.linkData.name}">
    <div id="container">
        <div id="topNav">
            ${topLinkbar}
        </div>
        <div id="sidebar1">
            ${siblingNavbar}
        </div>
        <div id="content1">
            <h1>${data.linkData.longName}</h1>
            ${data.chamberInformationContentDecoded}
        </div>
        <div id="content2">
            ${gallery}
        </div>
    </div>
</body>
</html>