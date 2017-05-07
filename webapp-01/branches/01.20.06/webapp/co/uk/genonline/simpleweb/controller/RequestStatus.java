package co.uk.genonline.simpleweb.controller;

/**
 * Created with IntelliJ IDEA.
 * @User: thomassecondary
 * @Date: 24/09/2012
 * @Time: 12:21
 *
 * Object used for management of status information, including a status message, across sessions.
 * For example, if a screen is added, the next action will be to re-display the editIndex page.  This will be
 * a new request but the editIndex page should include the message that the new screen was added successfully.  In
 * order to do that the status has to be maintained at session level.
 */
public class RequestStatus {
    String statusMessage;

    /**
     * Used to indicate the severity of the message. Strictly speaking should be Enum but is String for now.
     * Values are 'info', 'warning', 'error'.  Used within editIndex.jsp as class of status message element which
     * is picked up by css to generate a different colour for different status types.
     */
    String statusType;
    boolean messageDisplayed; // Used to indicate whether a message has been displayed yet

    public boolean isMessageDisplayed() {
        return messageDisplayed;
    }

    public String getStatusMessage() {
        messageDisplayed = true;
        return statusMessage;
    }

    public String getStatusType() {
        messageDisplayed = true;
        return statusType;
    }

    public RequestStatus() {
        resetStatusMessage();
    }

    public void resetStatusMessage() {
        statusMessage = "";
        statusType = "none";
        messageDisplayed = false;
    }

    public void setStatusMessage(String message, String type) {
        statusMessage = message;
        statusType = type;
        messageDisplayed = false;
    }

    public String toString() {
        return statusType + " : " + statusMessage;
    }
}
