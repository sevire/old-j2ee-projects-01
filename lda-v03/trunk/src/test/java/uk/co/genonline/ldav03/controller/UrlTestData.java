package uk.co.genonline.ldav03.controller;

import org.springframework.http.HttpStatus;

/**
 * Used to store data related to a URL test.  In particular includes the following:
 * - URL to test
 * - Expected HTTP Status
 * - Expected View returned from Controller
 */
public class UrlTestData {
    private String url;
    private HttpStatus status;
    private String viewName;
    private String attributeName;
    private String recordName;


    public String getAttributeName() {
        return attributeName;
    }

    public String getRecordName() {
        return recordName;
    }

    public UrlTestData(String url, HttpStatus status, String viewName, String attributeName, String recordName) {
        this.url = url;
        this.status = status;
        this.viewName = viewName;
        this.attributeName = attributeName;
        this.recordName = recordName;
    }

    public String getViewName() {
        return viewName;
    }

    public String getUrl() {
        return url;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String toString() {
        return String.format("URL: %s, Status: %s, View Name: %s, Expected Attribute: %s, Expected Record: %s",
                url, status, viewName, attributeName, recordName);
    }

/*
    public Object[] toArray() {
        Object[] data = {url, status, viewName, attributeName, recordName};
        return data;
    }
*/
}
