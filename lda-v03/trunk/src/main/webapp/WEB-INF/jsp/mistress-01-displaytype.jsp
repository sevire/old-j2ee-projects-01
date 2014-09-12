<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 25/08/2014
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>
    <script src="/js/injectContent.js"></script>
    <title>Display Type 01</title>
    <style>
        #status {
            font-family: monospace;
            padding: 10px;
        }
        #status.info {
            background-color: lightgreen;
            color: green;
        }
        #status.warn {
            background-color: lightyellow;
            color: yellow;
        }
        #status.error {
            background-color: palevioletred;
            color: red;
        }

    </style>
</head>
<body>
<a href="javascript:updatePage()">UPDATE PAGE</a>

<div id="container">
    <img src="/galleryimage/courtney/A05.jpg" />
    <p id="status" class="info"/>
    <div id="sContent" />
    <img src="/galleryimage/courtney/SID_2860-Edit.jpg" />
</div>

</body>
</html>
