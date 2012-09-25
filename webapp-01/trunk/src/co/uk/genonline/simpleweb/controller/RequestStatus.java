package co.uk.genonline.simpleweb.controller;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 24/09/2012
 * Time: 12:21
 * To change this template use File | Settings | File Templates.
 */
public class RequestStatus {
    String statusMessage;
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

    RequestStatus() {
        resetStatusMessage();
    }

    protected void resetStatusMessage() {
        statusMessage = "";
        statusType = "none";
        messageDisplayed = false;
    }

    protected void setStatusMessage(String message, String type) {
        statusMessage = message;
        statusType = type;
        messageDisplayed = false;
    }
}
