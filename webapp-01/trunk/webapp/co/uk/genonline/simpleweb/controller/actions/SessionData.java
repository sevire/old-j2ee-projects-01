package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.ConfigurationEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 17/06/2013
 * Time: 08:24
 *
 * This class is used to store everything which needs to be persisted between requests for a session.  Will be stored
 * as a parameter within the HttpSession variable and retrieved for each new request relating to that session.
 *
 * Encapsulates data objects used by the application (e.g. Screen, ConfigItems)
 */
public class SessionData {
    private final ConfigurationEntity configItems; //ToDo: Review whether should be storing configuration data here or in context
    private final ScreensEntity screen;
    private int requestCount = 0; // Use mainly for debugging to track requests for same session

    public SessionData(ScreensEntity screen) {
        this.screen = screen;
        this.configItems = null;
    }

    public SessionData(ConfigurationEntity configItems) {
        this.screen = null;
        this.configItems = configItems;
    }

    public SessionData(ScreensEntity screen, ConfigurationEntity configItems) {
        this.screen = screen;
        this.configItems = configItems;
    }

    public ScreensEntity getScreen() {
        return screen;
    }

    public ConfigurationEntity getConfigItems() {
        return configItems;
    }

    public void incrementRequestCount() {
        requestCount += 1;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public String toString() {
        return String.format("SessionData: Request number = <%d>, Screen object = <%s>, screen name = <%s>", this.requestCount, screen.toString(), screen.getName());
    }
}
