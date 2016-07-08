<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link rel="stylesheet" href="css/main.css" type="text/css" media="all">
    <link rel="icon" type="image/png" href="favicon.png">
    <title>Lucifer's Dark Angel - ${helper.screen.screenTitleLong}</title>
    <link rel="shortcut icon" href="../favicon.ico">
    <%--<link rel="stylesheet" type="text/css" href="css/demo.css" />--%>
    <%--<link rel="stylesheet" type="text/css" href="css/style.css" />--%>
    <link rel="stylesheet" type="text/css" href="css/elastislide.css" />
    <link href='http://fonts.googleapis.com/css?family=PT+Sans+Narrow&v1' rel='stylesheet' type='text/css' />
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css' />
    <noscript>
        <style>
            .es-carousel ul{
                display:block;
            }
        </style>
    </noscript>
    <script id="img-wrapper-tmpl" type="text/x-jquery-tmpl">
        <div class="rg-image-wrapper">
            {{if itemsCount > 1}}
            <div class="rg-image-nav">
                <a href="#" class="rg-image-nav-prev">Previous Image</a>
                <a href="#" class="rg-image-nav-next">Next Image</a>
            </div>
            {{/if}}
            <div class="rg-image"></div>
            <div class="rg-loading"></div>
            <div class="rg-caption-wrapper">
                <div class="rg-caption" style="display:none;">
                    <p></p>
                </div>
            </div>
        </div>
    </script>
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
                <li class='headerLink'>${mistressScreenData.homePageLink}</li>
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
            <div id='gallery' class='es-carousel'>
                    ${mistressScreenData.galleryHtml}
            </div>
        </core:if>
    </div>
    <div id='footer'>
</div>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/jquery.elastislide.js"></script>
<script type="text/javascript" src="js/gallery.js"></script>
</body>
</html>