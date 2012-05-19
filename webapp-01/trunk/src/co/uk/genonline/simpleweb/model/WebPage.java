package co.uk.genonline.simpleweb.model;

import com.petebevin.markdown.HTMLDecoder;
import com.petebevin.markdown.MarkdownProcessor;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 09/05/2012
 * Time: 13:35
 * To change this template use File | Settings | File Templates.
 */
public class WebPage {
    MarkdownProcessor markdownDecoder = new MarkdownProcessor();
    private Map<String, String> DataFields = new HashMap<String, String>();
    private String screenName;
    private Connection connection;
    Logger logger;

    public WebPage(Connection connection) {
        DataFields.put("screenName", "");
        DataFields.put("screenTitleShort", "");
        DataFields.put("screenTitleLong", "");
        DataFields.put("screenContents", "");
        logger = Logger.getLogger("WebPage");
        logger.setLevel(Level.ALL);
        this.connection = connection;
    }

    void getScreen(String name) {

        screenName = name;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            logger.info(String.format("About to retrieve data for screen with name %s", name));
            rs = stmt.executeQuery(String.format("SELECT ScreenTitleShort, ScreenTitleLong, ScreenContents FROM screens WHERE name='%s'", name));

            // Only expecting one row so don't need a loop
            if (rs.next()) {
                DataFields.put("screenContents", markdownDecoder.markdown(rs.getString("ScreenContents")));
                DataFields.put("screenTitleShort", rs.getString("ScreenTitleShort"));
                DataFields.put("screenTitleLong", rs.getString("ScreenTitleLong"));
           }
        }
        catch (SQLException ex) {
            // handle any errors
            logger.fatal("SQLException: " + ex.getMessage());
            logger.fatal("SQLState: " + ex.getSQLState());
            logger.fatal("VendorError: " + ex.getErrorCode());
        }
        finally {
            // it is a good idea to release
            // resources in a finally{} block
            // in reverse-order of their creation
            // if they are no-longer needed

            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException sqlEx) { } // ignore

                rs = null;
            }

            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException sqlEx) { } // ignore

                stmt = null;
            }
        }
    }

    public String getScreenName() {
        if (screenName != null) {
            return screenName;
        } else {
            return "(blank)";
        }
    }

    public String getField(String screenName, String fieldName) {
        logger.info(String.format("getField for screen <%s>, field <%s>", screenName, fieldName));
        if (screenName == null) {
            logger.warn(String.format("Supplied screen name <%s> is null", screenName));
            return "";
        } else if (this.screenName == null || !this.screenName.equals(screenName)) {
            getScreen(screenName);
        }
        return DataFields.get(fieldName);
    }

    public void updateScreen(String oldScreenName, String screenName, String screenTitleShort,
                             String screenTitleLong, String screenContents) {
        String sql;
        Statement statement;
        ResultSet rs;
        logger.info(String.format("Call to updateScreen for %s", screenName));
        try {
            statement = connection.createStatement();
            sql = String.format("UPDATE screens SET " +
                    "`name`=\"%s\", " +
                    "`screenTitleShort`=\"%s\", " +
                    "`screenTitleLong`=\"%s\", " +
                    "`ScreenContents`=\"%s\" WHERE `name`=\"%s\" LIMIT 1",
                    screenName, screenTitleShort, screenTitleLong, screenContents, oldScreenName);
            logger.info(String.format("About to UPDATE database..."));
            logger.info(String.format("SQL string <%s>", sql));
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
}
