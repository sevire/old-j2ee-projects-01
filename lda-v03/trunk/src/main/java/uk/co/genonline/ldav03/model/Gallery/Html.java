package uk.co.genonline.ldav03.model.Gallery;

import java.util.Iterator;
import java.util.List;

/**
 * Class to create general HTML markup used in the app.  Not meant to be comprehensive, I will add to it as I go.
 */
public class Html {
    String newline = System.getProperty("line.separator");

    public String constructHtmlTag(String tag, String clazz, String id, String otherAttributes, String content) {
        String html;

        html = "<" + tag;
        html += clazz.equals("") ? "" : "class=\"" + clazz + "\"";
        html += id.equals("") ? "" : "id=\"" + id + "\"";
        html += otherAttributes.equals("") ? "" : " " + otherAttributes;
        html += ">" + newline;
        html += content + newline;
        html += "/" + tag + ">" + newline;

        return html;
    }

    public String constructAnchorElement(String clazz, String id, String href, String content) {
        return constructHtmlTag("a", clazz, id, String.format("href=\"%s\""), content);
    }

    public String constructLiElement(String clazz, String id, String content) {
        return constructHtmlTag("li", clazz, id, "", content);
    }

    public String constructUlElement(String clazz, String id, String content) {
        return constructHtmlTag("ul", clazz, id, "", content);
    }

    public String constructImgElement(String clazz, String id, String content) {
        return constructHtmlTag("ul", clazz, id, "", content);
    }

    // ToDo: Add logic for alternate, first and last class values
    public String constructUlElement(String ulClass,
                                     String ulId,
                                     String liClass,
                                     boolean firstClass,
                                     boolean lastClass,
                                     boolean evenOddClass,
                                     List<String> liContentValues) {
        String liHtmlStrings = "";
        Iterator<String> iterator = liContentValues.listIterator();
        while (iterator.hasNext()) {
            liHtmlStrings += constructLiElement(liClass, "", iterator.next()) + newline;
        }
        return constructUlElement(ulClass, ulId, liHtmlStrings);
    }
}
