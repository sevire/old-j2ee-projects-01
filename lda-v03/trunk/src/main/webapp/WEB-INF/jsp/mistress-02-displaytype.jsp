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
    <div>
        ${testimonialLinkbar}
    </div>
    <div id="content1">
        <h1>${data.linkData.longName}</h1>
        ${data.mistressContentDecoded}
        <br />
        <table id="contactInformation" cols="2">
            <tr><td>Telephone:</td><td>${data.mistressTelephoneNumber}</td></tr>
            <tr><td>Email:</td><td>${data.mistressEmailAddress}</td></tr>
            <tr><td>Twitter:</td><td>${data.mistressTwitterUserName}</td></tr>
            <tr><td>Website:</td><td>${data.mistressWebsiteAddress}</td></tr>
            </tr>
        </table>
    </div>
    <div id="content2">
        <div id="gallery">
            ${gallery}
        </div>
    </div>
</div>
</body>
</html>
