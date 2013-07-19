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
    <title>Lucifer's Dark Angel - <%= addFlag ? "Add" : "Edit"%> : ${helper.screen.screenTitleLong}</title>
    <link rel="stylesheet" href="css/main.css" type="text/css" media="all" >
    <link rel="icon" type="image/png" href="favicon.png">
</head>
<body id='<%= addFlag ? "add" : "edit"%>'>
<div id='colLeft'></div>
<div id='colRight'></div>
<div id='colMiddle'>
    <div id='editForm'>
        <label for="updateScreen">Screen Details</label>
        <form id="updateScreen" name="editScreen" method="post" action='Controller.do'>
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
                        <input type='text' name='name' value="${helper.screen.name}" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Sort Key:</label>
                    </td>
                    <td>
                        <input type='text' name='sortKey' value="${helper.screen.sortKey}" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Screen Title Short:</label>
                    </td>
                    <td>
                        <input type='text' name='screenTitleShort' value="${helper.screen.screenTitleShort}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Screen Title Long:</label>
                    </td>
                    <td>
                        <input type='text' name='screenTitleLong' value="${helper.screen.screenTitleLong}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Screen Type:</label>
                    </td>
                    <td>
                        <select name='screenType'>
                            <option${helper.screen.screenType == 'Mistress' ? " selected='selected'" : ""}>Mistress</option>
                            <option${helper.screen.screenType == 'Chambers' ? " selected='selected'" : ""}>Chambers</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Enabled?:</label>
                    </td>
                    <td>
                        <input type="checkbox" name="enabledFlag" value="true"${helper.screen.enabledFlag ? " checked" : ""}>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>
                            Gallery?
                        </label>
                    </td>
                    <td>
                        <input type="checkbox" name="galleryFlag" value="true"${helper.screen.galleryFlag ? " checked" : ""}>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Screen Contents: </label>
                    </td>
                    <td>
                        <textarea rows="10" cols="60" name="screenContents">${helper.screen.screenContents}</textarea>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Meta Description: </label>
                    </td>
                    <td>
                        <textarea rows="4" cols="60" name="metaDescription">${helper.screen.metaDescription}</textarea>
                    </td>
                </tr>
                <tr><td>
                    <input type='submit' name=<%=addFlag ? "'addButton'" : "'updateButton'" %> value=<%=addFlag ? "'Add'" : "'Update'"%>>
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