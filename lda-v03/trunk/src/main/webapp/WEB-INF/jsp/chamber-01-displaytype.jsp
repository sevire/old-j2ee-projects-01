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
    <title>${chamberInformationData.chamberInformationLongName}</title>
</head>
<body>
    <div id="content">
        <div>
            ${chamberInformationData.chamberInformationContentDecoded}
        </div>
        <div>
            ${gallery.html}
        </div>
    </div>

</body>
</html>