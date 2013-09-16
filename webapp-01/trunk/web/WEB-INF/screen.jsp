<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 10/05/2012
  Time: 08:10
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Lucifer's Dark Angel - ${helper.screen.screenTitleLong}</title>
    <meta name="description" content="${helper.screen.metaDescription}">
    <link rel="stylesheet" href="css/main.css" type="text/css" media="all" >
    <link rel="icon" type="image/png" href="favicon.png">
    <style>
        div#gallery img {
            max-width: ${maxImgWidth}px;
            max-height: ${maxImgHeight}px;
        }

        table.gallery td {
            height: ${maxImgHeight+10}px;
            width: ${maxImgWidth+10}px;
        }

    </style>
</head>

<body id='${helper.screen.name}'>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-40866431-1', 'lucifersdarkangel.co.uk');
  ga('send', 'pageview');

</script>
<div id='colLeft'>
</div>
<div id='colRight'>
</div>
<div id='colMiddle'>
    <div id='bannerText'>
        <img alt="Lucifers Dark Angel - Princess Lucina" src="site_images/header-white-v01.png" />
<%--
        <h1><span class='first'>L</span>ucifer's <span class='first'>D</span>ark <span class='first'>A</span>ngel</h1>
        <h2><span class='first'>P</span>rincess <span class='first'>L</span>ucina <span class='xfirst'>O</span>f <span class='first'>M</span>anchester</h2>
--%>
    </div>
    <div class='header'>
        <ul>
            <li class='headerLink'>${homePage}</li>
            <%--<core:if test='${blogLink != null}'>--%>
                <li class='headerLink'>${blogLink}</li>
            <%--</core:if>--%>
            <%--<li class='headerLink'><core:out value="${mistressPageLink}" escapeXml="false"/></li>--%>
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