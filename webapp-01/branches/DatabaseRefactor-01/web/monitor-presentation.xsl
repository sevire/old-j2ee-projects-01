<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">

    <xsl:output method="html" version="5.0"
                encoding="UTF-8" indent="yes"/>

    <xsl:template match="/">
        <html>
            <head>
                <xsl:text disable-output-escaping="yes">&lt;!DOCTYPE html&gt;</xsl:text>
                <meta name="viewport" content="width=device-width, initial-scale=1" />
                <script src="https://code.jquery.com/jquery-3.2.1.min.js" integrity="sha256-hwg4gsxgFZhOsEEamdOYGBf13FyQuiTwlAQgxVSNgt4=" crossorigin="anonymous" />
                <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous"/>
                <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous" />
                <link rel="stylesheet" type="text/css" href="../css/monitor.css"/>
            </head>
            <body>
                <xsl:apply-templates select="*" />
            </body>
        </html>

    </xsl:template>

    <xsl:template match="monitor">
        <div class="container-fluid">
            <table class="table table-striped table-bordered">
                <thead class="thead-default">
                    <tr>
                        <th>Category</th>
                        <th>Name</th>
                        <th>Data</th>
                    </tr>
                </thead>
                <tbody>
                    <xsl:for-each select="item">
                        <xsl:sort select="category" />
                        <tr>
                            <td>
                                <xsl:value-of select="category" />
                            </td>
                            <td>
                                <xsl:choose>
                                    <xsl:when test="summary = 'true'">Summary</xsl:when>
                                    <xsl:otherwise>
                                        <xsl:value-of select="name" />
                                    </xsl:otherwise>
                                </xsl:choose>
                            </td>
                            <td>
                                <xsl:apply-templates select="data/displayData" />
                            </td>
                        </tr>
                    </xsl:for-each>
                </tbody>
            </table>
        </div>

    </xsl:template>

    <xsl:template match="data/displayData">
        <table class="table table-striped table-bordered">
        <xsl:for-each select="entry">
            <tr>
                <td class="monitor-label"><xsl:value-of select="key" /></td>
                <td class="monitor-value"><xsl:value-of select="value" /></td>
            </tr>
        </xsl:for-each>
        </table>
    </xsl:template>

<!--    <xsl:template match="data[@xsi:type='galleryStatus']">
        <table class="table table-striped">
            <tr>
                <td class="monitor-label"><xsl:text>Number of Images</xsl:text></td>
                <td><xsl:value-of select="numImages" /></td>
            </tr>
            <tr>
                <td class="monitor-label"><xsl:text>Gallery Error Status</xsl:text></td>
                <td><xsl:value-of select="galleryError" /></td>
            </tr>
            <tr>
                <td class="monitor-label"><xsl:text>Time Created</xsl:text></td>
                <td><xsl:value-of select="timeCreated" /></td>
            </tr>
            <tr>
                <td class="monitor-label"><xsl:text>Number Of Requests</xsl:text></td>
                <td><xsl:value-of select="numRequests" /></td>
            </tr>
            <tr>
                <td class="monitor-label"><xsl:text>Gallery Has Been Modified?</xsl:text></td>
                <td><xsl:value-of select="galleryModified" /></td>
            </tr>
        </table>
    </xsl:template>-->


</xsl:stylesheet>