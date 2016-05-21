package co.uk.genonline.simpleweb.controller;

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
 * Encapsulates data objects used by the application which need to be persisted between
 * sessions (e.g. Screen)
 */
public class SessionData {
    static int sessionCount;

    //private final ConfigurationEntity configItems; //ToDo: Review whether should be storing configuration data here or in context
    private ScreensEntity screen;
    private int requestCount = 0; // Use mainly for debugging to track requests for same session

    public SessionData(ScreensEntity screen) {
        this.sessionCount++;
        this.screen = screen;
    }

    /**
     * Setter will be used during processing of a request to replace screen object with another one so that it will
     * still be stored within the session.
     *
     * @param screen
     */
    public void setScreen(ScreensEntity screen) {
        this.screen = screen;
    }

    public ScreensEntity getScreen() {
        return screen;
    }

    public void incrementRequestCount() {
        requestCount += 1;
    }

    public int getRequestCount() {
        return requestCount;
    }

    public String toString() {
        return String.format("SessionData: Session Cound: <%d>, Request number = <%d>, Screen object = <%s>, screen name = <%s>", sessionCount, this.requestCount, screen.toString(), screen.getName());
    }
}
