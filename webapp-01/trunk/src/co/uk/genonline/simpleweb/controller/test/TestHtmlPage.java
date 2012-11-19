package co.uk.genonline.simpleweb.controller.test;

import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.net.URL;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 13/11/2012
 * Time: 08:11
 * To change this template use File | Settings | File Templates.
 */
public class TestHtmlPage {
    HtmlPage page;

    public TestHtmlPage(HtmlPage page) {
        this.page = page;
    }

    public TestHtmlPage(URL originatingUrl, WebResponse webResponse, WebWindow webWindow) {
        page = new HtmlPage(originatingUrl, webResponse, webWindow);
    }

    public boolean isBodyId(String bodyIdValue) {
        HtmlElement bodyElement = (HtmlElement)page.getElementsByTagName("body").item(0);
        return bodyElement.getAttribute("id").equals(bodyIdValue);
    }

    public HtmlPage getPage() {
        return page;
    }

    public void setPage(HtmlPage page) {
        this.page = page;
    }

    public String getBodyId() {
        HtmlElement bodyElement = (HtmlElement)page.getElementsByTagName("body").item(0);
        return bodyElement.getAttribute("id");
    }
}
