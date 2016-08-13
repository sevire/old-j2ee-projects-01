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
    <title>Lucifer's Dark Angel - View Gallery ${gallery} - Image ${image}</title>
    <link rel="stylesheet" href="css/main.css" type="text/css" media="all" >
    <link rel="icon" type="image/png" href="favicon.png">
    <style>
        div#image img {
            max-width: ${maxWidth}px;
        }
    </style>
</head>

<body>
<script>
  (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
  (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
  m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
  })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

  ga('create', 'UA-40866431-1', 'lucifersdarkangel.co.uk');
  ga('send', 'pageview');

</script><div id='colLeft'>
</div>
<div id='colRight'>
</div>
<div id='colMiddle'>
    <div id="image">
        <img src='${image}' />
    </div>
</div>
<div id='footer'>
</div>
</body>
</html>