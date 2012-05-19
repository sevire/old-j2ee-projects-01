package co.uk.genonline.simpleweb.model.bean;

import com.petebevin.markdown.MarkdownProcessor;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 15/05/2012
 * Time: 06:58
 * To change this template use File | Settings | File Templates.
 */
public class PageData {
    protected String name;
    protected String screenTitleShort;
    protected String screenTitleLong;
    protected String screenContents;

    Logger logger;
    Connection connection;
    MarkdownProcessor markdownDecoder = new MarkdownProcessor();

public PageData(Connection connection) {
    logger = Logger.getLogger("PageData");
    logger.setLevel(Level.ALL);

    this.connection = connection;
}
    public String getName() {
        if (name == null) {
            return "";
        } else {
            return name;
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getScreenTitleShort() {
        if (screenTitleShort == null || screenTitleShort.equals("")) {
            return getName();
        } else {
            return screenTitleShort;
        }
    }

    public void setScreenTitleShort(String screenTitleShort) {
        this.screenTitleShort = screenTitleShort;
    }

    public String getScreenTitleLong() {
        if (screenTitleLong == null || screenTitleLong.equals("")) {
            return getScreenTitleShort();
        } else {
            return screenTitleLong;
        }
    }

    public void setScreenTitleLong(String screenTitleLong) {
        this.screenTitleLong = screenTitleLong;
    }

    public String getScreenContents() {
        return screenContents;
    }

    public void setScreenContents(String screenContents) {
        this.screenContents = screenContents;
    }

    public void populateScreen() {
        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = connection.createStatement();
            logger.info(String.format("About to retrieve data for screen with name %s", name));
            rs = stmt.executeQuery(String.format("SELECT ScreenTitleShort, ScreenTitleLong, ScreenContents FROM screens WHERE name='%s'", name));

            // Only expecting one row so don't need a loop
            if (rs.next()) {
                screenContents = markdownDecoder.markdown(rs.getString("ScreenContents"));
                screenTitleShort = rs.getString("ScreenTitleShort");
                screenTitleLong = rs.getString("ScreenTitleLong");
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
                    rs = null;
                } catch (SQLException sqlEx) { } // ignore
            }

            if (stmt != null) {
                try {
                    stmt.close();
                    stmt = null;
                } catch (SQLException sqlEx) { } // ignore
            }
        }
    }
}
