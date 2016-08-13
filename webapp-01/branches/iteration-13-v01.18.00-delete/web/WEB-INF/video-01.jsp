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

    <link rel="icon" type="image/png" href="favicon.png">
    <title>Manchester Mistress Princess Lucina - ${screenData.screen.screenTitleLong}</title>
</head>

<body id='${helper.screen.name}'>

<div id="container">

    <div id="header">
        <div id='contactDetails'>
            <p class='leftFloat'>Call me on 07719 415496</p>
            <p class='rightFloat'>Email me on <a href='mailto:princess_lucina@yahoo.co.uk'>princess_lucina@yahoo.co.uk</a></p>
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
    <ul class='mainLinkBar'>
        <li class='headerLink'>${screenData.homePageLink}</li>
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
    </div>

    <div id="footer">
        <ul class='mainLinkBar'>
            <li class='headerLink'>${screenData.homePageLink}</li>
            <li class='headerLink'>${screenData.blogLink}</li>
            <li class='headerLink'><core:out value="${screenData.mistressPageLink}" escapeXml="false"/></li>
            <li class='headerLink'><a href="http://groups.yahoo.com/group/lucifersdarkangel/">Yahoo!&nbsp;Group</a></li>
        </ul>
    </div>
    <div id="contact">
        <p>
            Manchester Mistress, Manchester Dominatrix, UK Mistress, UK Dominatrix, Mistress Manchester,
            Northwest Mistress, BDSM, CBT, Dungeon, Chamber, Manchester Domination, Dominatrix Manchester,
            Dungeon Manchester, Domination Manchester, Mistress Manchester, Manchester Domme, Manchester Femdom,
            Femdom, CP, Corporal Punishment, Sadomasochism, Domination, Manchester Domination.
        </p>
    </div>

</div>
</body>
</html>