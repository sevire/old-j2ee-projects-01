<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,700,600,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Great+Vibes' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
    <link rel="stylesheet" href="css/mistress-02.css" type="text/css" media="all">
    <link rel="icon" type="image/png" href="favicon.png">
    <title>Princess Lucina - ${helper.screen.screenTitleLong}</title>
</head>

<body id='${helper.screen.name}'>

<div id="container">

    <div id="header">
        <div id='contactDetails'>
            <p class='leftFloat'>Call me on 07719 415496</p>
            <p class='rightFloat'>Email me on <a href='mailto:princess_lucina@yahoo.co.uk'>princess_lucina@yahoo.co.uk</a></p>
        </div>
        <div id='leftImage' class='headerImage'>
            <img height='250px' src='site_images/B44-cropped.png' />
        </div>
        <div id='innerHeader'>
            <div id="banner">
                <h1><span class='first'>P</span>rincess <span class='first'>L</span>ucina <span class='xfirst'>O</span>f <span class='first'>M</span>anchester</h1>
                <h2><span class='first'>L</span>ucifer's <span class='first'>D</span>ark <span class='first'>A</span>ngel</h2>
            </div>
        </div>
        <div id='rightImage' class='headerImage'>
            <img height='250px' src='site_images/B52.png' />
        </div>
    </div>
    <ul class='mainLinkBar'>
        <li class='headerLink'>${screenData.homePage}</li>
        <li class='headerLink'>${screenData.blogLink}</li>
        <li class='headerLink'><core:out value="${screenData.mistressPageLink}" escapeXml="false"/></li>
        <li class='headerLink'><a href="http://groups.yahoo.com/group/lucifersdarkangel/">Yahoo!&nbsp;Group</a></li>
    </ul>


    <div id="leftColumn">
        ${screenData.chambersLinkBar}
    </div>

    <div id="content">
        <div id="pageText">
            <h1>${screenData.screen.screenTitleLong}</h1>
            ${screenData.screen.screenContents}
        </div>
        <core:if test='${(screenData.galleryHtml != null) && (screenData.galleryHtml != "")}'>
            <div id='gallery'>
                    ${screenData.galleryHtml}
            </div>
        </core:if>
        </div>

    <div id="footer">
        <ul class='mainLinkBar'>
            <li class='headerLink'>${screenData.homePage}</li>
            <li class='headerLink'>${screenData.blogLink}</li>
            <li class='headerLink'><core:out value="${screenData.mistressPageLink}" escapeXml="false"/></li>
            <li class='headerLink'><a href="http://groups.yahoo.com/group/lucifersdarkangel/">Yahoo!&nbsp;Group</a></li>
        </ul>
    </div>

</div>

</body>
</html>