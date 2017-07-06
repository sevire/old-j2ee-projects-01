package co.uk.genonline.simpleweb.web;


import co.uk.genonline.simpleweb.web.gallery.HtmlAttribute;
import com.hp.gagawa.java.elements.A;

import java.util.Iterator;
import java.util.Map.Entry;

/**
 * Created by thomassecondary on 04/04/16.
 */
public class WebLink {
    String url;
    String linkText;
    HtmlAttributeCollection attributes;

    public WebLink(String url, String linkText, HtmlAttributeCollection attributes) {
        this.url = url;
        this.linkText = linkText;
        this.attributes = attributes;
    }

    public String getHtml() {
        A anchorElement = new A();
        anchorElement = anchorElement.setHref(url);
        anchorElement = anchorElement.appendText(linkText);

        Iterator<Entry<String, HtmlAttribute>> attributeIterator = attributes.entrySet().iterator();
        while (attributeIterator.hasNext()) {
            Entry<String, HtmlAttribute> attributeEntry = attributeIterator.next();
            String attributeName = attributeEntry.getKey();
            String attributeValue = attributeEntry.getValue().getHtmlAttributeString();

            anchorElement.setAttribute(attributeName, attributeValue);
        }
        String html = anchorElement.write();
        return html;
    }
}
