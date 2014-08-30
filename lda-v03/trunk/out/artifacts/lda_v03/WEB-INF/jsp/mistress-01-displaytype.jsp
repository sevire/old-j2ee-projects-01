<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 25/08/2014
  Time: 13:14
  To change this template use File | Settings | File Templates.
--%>
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
<div id="container">
    <p id="status" class="info"/>
        <div id="sContent" />
</div>

</body>
</html>
