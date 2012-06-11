package co.uk.genonline.simpleweb.web;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 10/06/2012
 * Time: 09:36
 * To change this template use File | Settings | File Templates.
 */
public class WebHelper {
    public String generateEditScreenLink(String name, String screenTitleShort, String screenTitleLong) {
        return String.format("<a href='view?screen=%s'>Edit</a>");
    }

    public String generateLinkTableRow(String name, String screenTitleShort, String screenTitleLong) {
        return String.format("<tr><td>%s</td><td>%s</td><td>%s</td></tr>",
                screenTitleShort,
                screenTitleLong,
                generateEditScreenLink(name, screenTitleShort, screenTitleLong));
    }
}
