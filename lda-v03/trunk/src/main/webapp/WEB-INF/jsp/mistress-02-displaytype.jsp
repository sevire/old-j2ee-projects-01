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
    <title>${mistressData.linkData.longName}</title>
</head>
<body id="${mistressData.linkData.name}">
<div id="wrapper">
    <div id="header">
        ${topLinkbar}
    </div>
    <div id="sidebar1">
        ${mistressNavbar}
    </div>
    <div>
        ${testimonialLinkbar}
    </div>
    <div id="content1">
        <h1>${mistressData.linkData.longName}</h1>
        ${mistressData.mistressContentDecoded}
    </div>
    <div id="content2">
        <div id="gallery">
            ${gallery}
        </div>
    </div>
</div>
</body>
</html>
