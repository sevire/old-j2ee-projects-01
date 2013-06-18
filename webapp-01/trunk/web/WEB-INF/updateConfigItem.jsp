<%@ page import="co.uk.genonline.simpleweb.controller.RequestStatus" %>
<%--
  Created by IntelliJ IDEA.
  User: thomassecondary
  Date: 13/05/2012
  Time: 23:01

  Used for both add and edit screens.
--%>
<!DOCTYPE html>
<html>
<head>
    <% Boolean addFlagObject = (Boolean) request.getAttribute("addFlag");%>
    <% boolean addFlag = addFlagObject;%>
    <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
    <title>Lucifer's Dark Angel - <%= addFlag ? "Add" : "Edit"%> Config Item</title>
    <link rel="stylesheet" href="css/main.css" type="text/css" media="all" >
    <link rel="icon" type="image/png" href="favicon.png">
</head>
<body id='<%= addFlag ? "add" : "edit"%>'>
<div id='colLeft'></div>
<div id='colRight'></div>
<div id='colMiddle'>
    <div id='editForm'>
        <label for="updateConfigItem">Config Item Details</label>
        <form id="updateConfigItem" name="editConfigItem" method="post" action='Controller.do'>
            <table>
                <tr class='statusRow <%=((RequestStatus)session.getAttribute("requestStatus")).getStatusType()%>'>
                                  <td colspan="2"><%=((RequestStatus)session.getAttribute("requestStatus")).getStatusMessage()%></td>
                              </tr>
                <col class='label'/>
                <tr>
                    <td>
                        <label>Name:</label>
                    </td>
                    <td>
                        <input type='text' name='name' value="${configItem.name}" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Value:</label>
                    </td>
                    <td>
                        <input type='text' name='value' value="${configItem.value}" />
                    </td>
                </tr>
                <tr><td>
                    <input type='submit' name=<%=addFlag ? "'addConfigButton'" : "'updateConfigButton'" %> value=<%=addFlag ? "'Add'" : "'Update'"%>>
                </td></tr>
                <tr><td>
                    <input type='submit' name='cancelButton' value='Cancel'>
                </td></tr>
            </table>
        </form>
    </div>
</div>
</body>
</html>