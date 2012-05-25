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
    <title>Lucifer's Dark Angel - ${helper.data.screenTitleLong}</title>
    <link rel="stylesheet" href="./css/main.css" type="text/css" media="all" >
</head>

<body id='${helper.data.name}'>
<div id='colLeft'>
</div>
<div id='colRight'>
</div>
<div id='colMiddle'>
    <%@ include file="header.jsp" %>
    <div id="pageText">
        ${helper.data.screenContents}
    </div>
</div>
<div id='footer'>
</div>
</body>
</html>