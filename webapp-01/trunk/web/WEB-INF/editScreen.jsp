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
    <title>Lucifer's Dark Angel - Edit : ${helper.data.screenTitleLong}</title>
    <link rel="stylesheet" href="../css/main.css" type="text/css" media="all" >
</head>
<body id='Edit'>
<div id='colLeft'></div>
<div id='colRight'></div>
<div id="'colMiddle">
    <div id="editForm">
        <label for="editScreen">Screen Details</label>
        <form id="editScreen" method="post" action='Controller.do'>
            <table>
                <col class='label'/>
                <tr>
                    <td>
                        <label>Name:</label>
                    </td>
                    <td>
                        <input type='text' name='name' value="${helper.data.name}" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Enabled?:</label>
                    </td>
                    <td>
                        <input type="checkbox" name="enabledFlag" value="Enabled"${helper.data.enabledFlag ? " checked" : ""}>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Screen Title Short:</label>
                    </td>
                    <td>
                        <input type='text' name='screenTitleShort' value="${helper.data.screenTitleShort}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Screen Title Long:</label>
                    </td>
                    <td>
                        <input type='text' name='screenTitleLong' value="${helper.data.screenTitleLong}">
                    </td>
                </tr>
                <tr>
                    <td>
                        <label>Screen Type:</label>
                    </td>
                    <td>
                        <select name='screenType'>
                            <option${helper.data.screenType == 'Mistress' ? " selected='selected'" : ""}>Mistress</option>
                            <option${helper.data.screenType == 'Chambers' ? " selected='selected'" : ""}>Chambers</option>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                <label>Screen Contents: </label>
                    </td>
                    <td>
                <textarea rows="10" cols="60" name="screenContents" >
${helper.data.screenContents}
                </textarea>
                    </td>
                </tr>
                <tr><td>
                    <input type='submit' name='updateButton' value='Update'>
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