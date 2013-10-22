<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link rel="stylesheet" href="css/main.css" type="text/css" media="all">
    <link rel="icon" type="image/png" href="favicon.png">
    <title>Lucifer's Dark Angel - ${helper.screen.screenTitleLong}</title>
</head>

<body id='${helper.screen.name}'>

        <div id='colLeft'>
        </div>
    <div id='colRight'>
    </div>
    <div id='colMiddle'>
        <div id='bannerText'>
            <img alt="Lucifers Dark Angel - Princess Lucina" src="site_images/header-white-v01.png"/>
        </div>
        <div class='header'>
            <ul>
                <li class='headerLink'>${mistressScreenData.homePage}</li>
                <core:if test='${mistressScreenData.blogLink != null}'>
                    <li class='headerLink'>${mistressScreenData.blogLink}</li>
                </core:if>
                <li class='headerLink'><core:out value="${mistressScreenData.mistressPageLink}" escapeXml="false"/></li>
                <li class='headerLink'><a href="http://groups.yahoo.com/group/lucifersdarkangel/">Yahoo!&nbsp;Group</a></li>
            </ul>
            <%--
                    ${mistressLinkBar}
            --%>
            <ul>
            </ul>
            ${mistressScreenData.chambersLinkBar}
        </div>
        <div id="pageText">
            <h3>${mistressScreenData.screen.screenTitleLong}</h3>
            ${mistressScreenData.screen.screenContents}
        </div>
        <core:if test='${(mistressScreenData.galleryHtml != null) && (mistressScreenData.galleryHtml != "")}'>
            <div id='gallery'>
                    ${mistressScreenData.galleryHtml}
            </div>
        </core:if>
    </div>
    <div id='footer'>
</div>
</body>
</html>