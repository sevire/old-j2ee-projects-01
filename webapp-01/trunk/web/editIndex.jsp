<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 13/05/2012
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Lucifer's Dark Angel - Edit : <%out.print((String)request.getAttribute("screenTitleLong"));%></title>
    <link rel="stylesheet" href="./css/main.css" type="text/css" media="all" >
</head>
<body id='Edit'>
<div id='colLeft'></div>
<div id='colRight'></div>
<div id="'colMiddle">
    <div id="editIndex">
        <%
            String[] pageData = (String[]) request.getAttribute("PageData");

            for (int pageCount=0; pageCount< (Integer) request.getAttribute("pageCount");pageCount++) {
                System.out.print();
            }

        %>
    </div>
</div>
</body>
</html>