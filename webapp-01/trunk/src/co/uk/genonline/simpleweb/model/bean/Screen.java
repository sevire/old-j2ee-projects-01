package co.uk.genonline.simpleweb.model.bean;

import com.petebevin.markdown.MarkdownProcessor;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 15/05/2012
 * Time: 06:58
 * To change this template use File | Settings | File Templates.
 */
public class Screen {
    protected String name;
    protected String screenTitleShort;
    protected String screenTitleLong;
    protected String screenContents;

    Logger logger;
    MarkdownProcessor markdownDecoder = new MarkdownProcessor();

public Screen() {
    logger = Logger.getLogger("Screen");
    logger.setLevel(Level.ALL);
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
}
