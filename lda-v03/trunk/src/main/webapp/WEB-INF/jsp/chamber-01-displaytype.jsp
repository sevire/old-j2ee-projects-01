<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 20/07/2014
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<jsp:useBean id="chamberInformationData" scope="request" type="uk.co.genonline.ldav03.model.chamber.ChamberInformation"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${chamberInformationData.linkData.longName}</title>
</head>
<body id="${chamberInformationData.linkData.name}">
    <div id="wrapper">
        <div id="header">
            ${topLinkbar}
        </div>
        <div id="sidebar1">
            ${chamberNavbar}
        </div>
        <div id="content1">
            <h1>${chamberInformationData.linkData.longName}</h1>
            ${chamberInformationData.chamberInformationContentDecoded}
        </div>
        <div id="content2">
            ${gallery}
        </div>
    </div>
</body>
</html>