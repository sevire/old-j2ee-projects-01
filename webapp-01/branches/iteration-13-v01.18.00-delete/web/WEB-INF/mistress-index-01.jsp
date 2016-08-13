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
    <link rel="stylesheet" href="css/mistress-05.css" type="text/css" media="all">
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
    <title>Manchester Mistress Princess Lucina - ${screenData.screenData.screensEntityDecorator.screenTitleLong}</title>
    <style>
        div.header-image img {
            width: 100%;
        }
        div#middle-header {
            display: inline-block;
            width: 68%;
            vertical-align: top;;
        }
    </style>
</head>

<body id='${helper.screen.name}'>

<div id="container">
    <div id="header">
        <div id="banner">
            <h1>Princess Lucina - Lucifer's Dark Angel</h1>

        </div>
        <div id="left-image" class="header-image">
            <%--<img src="site_images/B44-cropped.png" />--%>
            <img src='site_images/header_images/${screenData.screenHeader.headerImageLeft}' />
        </div>
        <div id="middle-header">
            <div id="contact-phone" class="contact">
                <img src="site_images/phone.png" />
                <p class='leftFloat'>07719 415496</p>
            </div>
            <div id="contact-email" class="contact">
                <img src="site_images/mail.png" />
                <p class='rightFloat'>
                    <a href='mailto:princess_lucina@yahoo.co.uk'>princess_lucina@yahoo.co.uk</a>
                </p>
            </div>
            <div id="externalLinks">
                <ul class='mainLinkBar'>
                    <li class='headerLink home'>
                        <a href="view?screen=lucina" title="My home page"><img src="site_images/home.png"/></a>
                    </li>
                    <li class='headerLink blog'>
                        <a href="http://manchestermistresslucina.blogspot.co.uk" title="Read my blog"><img src="site_images/Blog_Icon.png"/></a>
                    </li>
                    <li class='headerLink yahoo'>
                        <a href="http://groups.yahoo.com/group/lucifersdarkangel" title="My Yahoo group"><img src="site_images/yahoo.png"/></a>
                    </li>
                    <li class='headerLink twitter'>
                        <a href="https://twitter.com/intent/user?screen_name=Lucina_Princess" title="Follow me on Twitter"><img src="site_images/twitter-icon-80.png"/></a>
                    </li>
                </ul>
            </div>
        </div>
        <div id="right-image" class="header-image">
            <img src='site_images/header_images/${screenData.screenHeader.headerImageRight}' />
            <%--<img src="site_images/B44-cropped.png" />--%>
        </div>
    </div>
    <div class="menu-button">Menu</div>
    <div id="mainNav">
        <ul class="flexnav" data-breakpoint="800">
            <li>
                <a>Princess Lucina</a>
                <%=((MistressScreenData)request.getAttribute("screenData")).getScreenMenus().getMenu("lucinaLinkBar")%>
            </li>
            <li>
                <a>My Facilities</a>
                <%=((MistressScreenData)request.getAttribute("screenData")).getScreenMenus().getMenu("chambersLinkBar")%>
            </li>
            <li>
                <a>Galleries</a>
                <%=((MistressScreenData)request.getAttribute("screenData")).getScreenMenus().getMenu("galleryLinkBar")%>
            </li>
            <li>
                <a>Testimonials</a>
                <%=((MistressScreenData)request.getAttribute("screenData")).getScreenMenus().getMenu("testimonialLinkBar")%>
            </li>
            <li>
                <a>Other Mistresses</a>
                <%=((MistressScreenData)request.getAttribute("screenData")).getScreenMenus().getMenu("mistressLinkBar")%>
            </li>
        </ul>
    </div>
    <div id="content">
        <div id="pageText">
            <h1>${screenData.screenData.screensEntityDecorator.screenTitleLong}</h1>
            ${screenData.screenData.screensEntityDecorator.screenContentsHtml}
            ${screenData.screenMistressTable.mistressTable}
        </div>
        <core:if test='${(screenData.screenGallery.galleryData != null) && (screenData.screenGallery.galleryData != "")}'>
            <div id="rg-gallery" class="rg-gallery">
                <div class="rg-thumbs">
                    <!-- Elastislide Carousel Thumbnail Viewer -->
                    <div class="es-carousel-wrapper">
                        <div class="es-nav">
                            <span class="es-nav-prev">Previous</span>
                            <span class="es-nav-next">Next</span>
                        </div>
                        <div class="es-carousel">
                                ${screenData.screenGallery.galleryData}
                        </div>
                    </div>
                    <!-- End Elastislide Carousel Thumbnail Viewer -->
                </div><!-- rg-thumbs -->
            </div><!-- rg-gallery -->
        </core:if>
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
</body>
</html>