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
    <link rel="stylesheet" href="css/mistress-03.css" type="text/css" media="all">
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
    <title>Manchester Mistress Princess Lucina - ${screenData.screen.screenTitleLong}</title>
</head>

<body id='${helper.screen.name}'>

<div id="container">
    <div id="header">
        <div id='upperHeader'>
            <div class="contact left">
                <img src="site_images/phone.png" />
                <p class='leftFloat'>07719 415496</p>
            </div>

            <ul class='mainLinkBar'>
                <li class='headerLink home'>
                    <a href="http://localhost:8080/lda/view?screen=home" title="My home page"><img src="site_images/home.png"/></a>
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
            <div class="contact right">
                <p class='rightFloat'>
                    <a href='mailto:princess_lucina@yahoo.co.uk'>princess_lucina@yahoo.co.uk</a>
                </p>
                <img src="site_images/mail.png" />
            </div>
        </div>
        <div id='leftImage' class='headerImage'>
            <img height='250px' src='site_images/header_images/${screenData.headerImageLeft}' />
        </div>
        <div id='innerHeader'>
            <div id="banner">
                <h1><span class='first'>P</span>rincess <span class='first'>L</span>ucina <span class='xfirst'>O</span>f <span class='first'>M</span>anchester</h1>
                <h2><span class='first'>L</span>ucifer's <span class='first'>D</span>ark <span class='first'>A</span>ngel</h2>
            </div>
        </div>
        <div id='rightImage' class='headerImage'>
            <img height='250px' src='site_images/header_images/${screenData.headerImageRight}' />
        </div>
    </div>


    <div id="leftColumn" class="sideColumn">
<%--
        <h1 class="linkBarHeading">My Chambers</h1>
        ${screenData.chambersLinkBar}
--%>
        <h1 class="linkBarHeading">Princess Lucina</h1>
        ${screenData.lucinaLinkBar}
        <h1 class="linkBarHeading">Galleries</h1>
        ${screenData.galleryLinkBar}
    </div>
    <div id="rightColumn" class="sideColumn">
        <h1 class="linkBarHeading">Other Mistresses</h1>
        ${screenData.mistressLinkBar}
        <h1 class="linkBarHeading">My Slaves</h1>
        ${screenData.testimonialLinkBar}
    </div>

    <div id="content">
        <div id="pageText">
            <h1>${screenData.screen.screenTitleLong}</h1>
            ${screenData.screen.screenContents}
        </div>
        <core:if test='${(screenData.galleryHtml != null) && (screenData.galleryHtml != "")}'>
            <div id="rg-gallery" class="rg-gallery">
                <div class="rg-thumbs">
                    <!-- Elastislide Carousel Thumbnail Viewer -->
                    <div class="es-carousel-wrapper">
                        <div class="es-nav">
                            <span class="es-nav-prev">Previous</span>
                            <span class="es-nav-next">Next</span>
                        </div>
                        <div class="es-carousel">
                                ${screenData.galleryHtml}
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
</body>
</html>