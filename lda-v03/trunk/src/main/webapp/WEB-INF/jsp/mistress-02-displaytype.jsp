<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 20/07/2014
  Time: 15:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${data.linkData.longName}</title>
    <link rel="stylesheet" href="/css/reset.css" type="text/css" media="all">
    <link rel="stylesheet" href="/css/main.css" type="text/css" media="all">
    <link rel="stylesheet" href="/css/flexnav.css" type="text/css" />

    <script type="text/javascript" src="/js/jquery-1.11.1.js" ></script>
    <script type="text/javascript" src="/js/jquery.flexnav.min.js" ></script>
</head>
<body id="${data.linkData.name}">
<div id="container">
    <div id="header">
           <div id="headerImage">
             <%--<img src="/images/siteimages/header-01.png" />--%>
           </div>
        <div id="mobileTopNav" class="menu-button">MENU</div>
        <div id="topNav" class="clearfix">
            <ul id="menu1" class="flexnav clearfix" data-breakpoint="800">
                <li><a href="#">Top Menu 1</a>
                    <ul>
                        <li><a href="#">Item 1</a></li>
                        <li><a href="#">Item 2</a></li>
                        <li><a href="#">Item 3</a></li>
                        <li><a href="#">Item 4</a></li>
                    </ul>
                </li>
                <li><a href="#">Top Menu 2</a>
                    <ul>
                        <li><a href="#">Item 5</a></li>
                        <li><a href="#">Item 6</a></li>
                        <li><a href="#">Item 7</a></li>
                        <li><a href="#">Item 8</a></li>
                    </ul>
                </li>
                <li><a href="#">Top Menu 3</a>
                    <ul>
                        <li><a href="#">Item 9</a></li>
                        <li><a href="#">Item 10</a></li>
                        <li><a href="#">Item 11</a></li>
                        <li><a href="#">Item 12</a></li>
                    </ul>
                </li>
                <li><a href="#">Top Menu 4</a>
                    <ul>
                        <li><a href="#">Item 13</a></li>
                        <li><a href="#">Item 14</a></li>
                        <li><a href="#">Item 15</a></li>
                        <li><a href="#">Item 16</a></li>
                    </ul>
                </li>
                <li><a href="#">Links</a>
                    <ul>
                        <li><a href="#">Item 17</a></li>
                        <li><a href="#">Item 18</a></li>
                        <li><a href="#">Item 19</a></li>
                        <li><a href="#">Item 20</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </div><%--



    <div id="topNav">
        ${topLinkbar}
    </div>
    <div id="sidebar1">
        ${siblingNavbar}
    </div>
    <div>
        ${testimonialLinkbar}
    </div>
    <div id="content1">
        <h1>${data.linkData.longName}</h1>
        ${data.mistressContentDecoded}
        <br />
        <table id="contactInformation" cols="2">
            <tr><td>Telephone:</td><td>${data.mistressTelephoneNumber}</td></tr>
            <tr><td>Email:</td><td>${data.mistressEmailAddress}</td></tr>
            <tr><td>Twitter:</td><td>${data.mistressTwitterUserName}</td></tr>
            <tr><td>Website:</td><td>${data.mistressWebsiteAddress}</td></tr>
            </tr>
        </table>
    </div>
    <div id="content2">
        <div id="gallery">
            ${gallery}
        </div>
    </div>--%>
</div>
<script>
$(".flexnav").flexNav();
</script>
</body>
</html>
