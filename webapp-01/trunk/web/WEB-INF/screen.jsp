<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link rel="stylesheet" href="css/main.css" type="text/css" media="all" >
    <link rel="icon" type="image/png" href="favicon.png">
</head>

<div id='colLeft'>
</div>
<div id='colRight'>
</div>
<div id='colMiddle'>
    <div id='bannerText'>
        <img alt="Lucifers Dark Angel - Princess Lucina" src="site_images/header-white-v01.png" />
    </div>
    <div class='header'>
        <ul>
            <li class='headerLink'>${homePage}</li>
            <core:if test='${blogLink != null}'>
                <li class='headerLink'>${blogLink}</li>
            </core:if>
            <li class='headerLink'><core:out value="${mistressPageLink}" escapeXml="false"/></li>
            <li class='headerLink'><a href="http://groups.yahoo.com/group/lucifersdarkangel/">Yahoo!&nbsp;Group</a></li>
        </ul>
<%--
        ${mistressLinkBar}
--%>
        <ul>
        </ul>
                ${chambersLinkBar}
    </div>
    <div id="pageText">
        <h3>${helper.screen.screenTitleLong}</h3>
        ${helper.screen.screenContents}
    </div>
        <% if (!(request.getAttribute("galleryHTML") == null || request.getAttribute("galleryHTML").equals(""))) {%>
        <div id='gallery'>
            ${galleryHTML}
        </div>
        <% } %>
</div>
<div id='footer'>
</div>
</body>
</html>