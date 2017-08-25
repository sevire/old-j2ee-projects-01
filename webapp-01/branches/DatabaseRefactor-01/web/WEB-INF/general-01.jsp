<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->

    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../favicon.png">

    <%@ include file="google-fonts.jspf" %>
    <%@ include file="flexnav-header-files.jspf" %>
    <%@ include file="bootstrap-css.jspf" %>
    <%@ include file="bootstrap-js-top.jspf" %>
    <%--<%@ include file="lda-styling.jspf" %>--%>

    <title>Title</title>
    <style>
        header h1 {
            font-size: 4.2vw;
        }
    </style>
</head>

<header>
    <div class="container">
        <div class="jumbotron">
            <div class="row">
                <div class="col-md-4">
                    HELLO
                </div>
                <div class="col-md-8">
                    Hello Again
                    <h1>Screen Title Long</h1>
                </div>
            </div>
        </div>
    </div>
</header>

<body>
    <div class="links"></div>
</body>

<footer>

    <%@ include file="bootstrap-js-bottom.jspf" %>
    <script>
        $(document).ready(function() {
            var linkData = ${screenData.linksData.jsonString}; // Note JS recognises this as JSON and parses it (surprisingly)
            var html = "<table class='table table-striped'>";
            html += "<thead>";
            html += "<tr>";
            html += "<th>" + "Number" + "</th>";
            html += "<th>" + "Name" + "</th>";
            html += "<th>" + "Status" + "</th>";
            html += "<th>" + "Days Since Check" + "</th>";
            html += "<th>" + "Date Of Next Check" + "</th>";
            html += "<th>" + "Time To Check?" + "</th>";
            html += "<th>" + "Url" + "</th>";
            html += "<th>" + "Banner Image" + "</th>";
            html += "<th>" + "Last Checked" + "</th>";
            html += "<th>" + "Next Action Type" + "</th>";
            html += "<th>" + "Next Action Notes" + "</th>";
            html += "</tr>";
            html += "</thead>";
            html += "<tbody>";

            for (row=0; row<linkData.length; row++) {
                html += "<tr>";
                html += "<td>" + linkData[row].number + "</td>";
                html += "<td>" + linkData[row].name + "</td>";
                html += "<td>" + linkData[row].status + "</td>";
                html += "<td>" + linkData[row].numDaysSinceChecked + "</td>";
                html += "<td>" + linkData[row].dateOfNextCheck + "</td>";
                html += "<td>" + linkData[row].timeToCheck + "</td>";
                html += "<td>" + linkData[row].url + "</td>";
                html += "<td>" + linkData[row].bannerImageName + "</td>";
                html += "<td>" + linkData[row].lastCheckedDate + "</td>";
                html += "<td>" + linkData[row].nextActionType + "</td>";
                html += "<td>" + linkData[row].nextActionNotes + "</td>";
                html += "</tr>";
            }
            html += "</tbody>";
            html += "</table>";
            $('div.links').html(html);
        })
    </script>

</footer>
</html>
