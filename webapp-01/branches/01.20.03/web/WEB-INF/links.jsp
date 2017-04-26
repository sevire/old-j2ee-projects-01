<%@ page import="co.uk.genonline.simpleweb.controller.screendata.MistressScreenData" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <link href='http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,800italic,400,300,700,600,800' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Eagle+Lake' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Great+Vibes' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=Eagle+Lake|Tangerine' rel='stylesheet' type='text/css'>
    <link href='http://fonts.googleapis.com/css?family=PT+Sans+Narrow&v1' rel='stylesheet' type='text/css' />
    <link href='http://fonts.googleapis.com/css?family=Pacifico' rel='stylesheet' type='text/css' />
    <link rel="stylesheet" href="css/reset.css" type="text/css" media="all">
    <link rel="stylesheet" href="css/contactMe.css" type="text/css" media="all">
    <link rel="stylesheet" href="flexNav/css/flexnav.css" type="text/css" media="all">
    <link rel="stylesheet" href="css/flexnavoverride-05.css" type="text/css" media="all">
    <meta name="keywords" content="mistress, mistresses, manchester, link, domina, lucina, links, princess">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <link rel="stylesheet" type="text/css" href="css/style.css" />
    <link rel="stylesheet" type="text/css" href="css/elastislide.css" />
    <noscript>
        <style>
            .es-carousel ul{
                display:block;
            }
        </style>
    </noscript>
    <script>
        (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
            (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
        })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

        ga('create', 'UA-40866431-1', 'lucifersdarkangel.co.uk');
        ga('send', 'pageview');

    </script>

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
    <link rel="icon" type="image/png" href="favicon.png">
    <title>Manchester Mistress Princess Lucina - Contact Me</title>
    <style>
        div.header-image img {
            width: 100%;
        }
        div#middle-header {
            display: inline-block;
            width: 68%;
            vertical-align: top;
        }
        table#linksTable {
            margin: 20px auto 0 auto;
        }

        table#linksTable td {
            padding: 5px 0;
            height: 60px;
            color: #000000;
            border: black solid 1px;
            background-color: #f54a40;
            text-align: center;
        }
    </style>
</head>

<body id='contactMe'>

<div id="container">
    <%@ include file="header-01.jspf" %>
    <div class="menu-button">Menu</div>
    <%@include file="main-menu-02.jspf"%>
    <div id="content">
        <div id="pageText">
            <h1>${screenData.screenData.screensEntityDecorator.screenTitleLong}</h1>
            ${screenData.screenData.screensEntityDecorator.screenContentsHtml}
        </div>
        <div class="links">

        </div>
    </div>

    <div id="footer">
        <p>
            Manchester Mistress, Manchester Dominatrix, UK Mistress, UK Dominatrix, Mistress Manchester,
            Northwest Mistress, BDSM, CBT, Dungeon, Chamber, Manchester Domination, Dominatrix Manchester,
            Dungeon Manchester, Domination Manchester, Mistress Manchester, Manchester Domme, Manchester Femdom,
            Femdom, CP, Corporal Punishment, Sadomasochism, Domination, Manchester Domination.
        </p>
    </div>

</div>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js"></script>
<script type="text/javascript" src="js/jquery.tmpl.min.js"></script>
<script type="text/javascript" src="js/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="js/jquery.elastislide.js"></script>
<script type="text/javascript" src="js/gallery.js"></script>
<script type="text/javascript" src="flexNav/js/jquery.flexnav.min.js"></script>
<script type="text/javascript">
    $('.flexnav').flexNav();
</script>
<script>
    $(document).ready(function() {
        var linkData = ${screenData.linksData.jsonString}; // Note JS recognises this as JSON and parses it (surprisingly)
        var html = "<table id='linksTable' class='table table-striped'>";
        html += "<tbody>";

        for (row=0; row<linkData.length; row++) {
            html += "<tr>";
            html += "<td>";
            html += "<a href='" + linkData[row].url + "'>";
            if (linkData[row].bannerImageName == "") {
                html += linkData[row].name;
            } else {
                html += "<img src='${screenData.siteData.staticFileRootURLPath}/" + "${screenData.linksData.linksRoot}/" + linkData[row].bannerImageName + "'img>";
            }
            html += "</a>";
            html += "</td>";
            html += "</tr>";
        }
        html += "</tbody>";
        html += "</table>";
        $('div.links').html(html);
    })
</script>

</body>
</html>

