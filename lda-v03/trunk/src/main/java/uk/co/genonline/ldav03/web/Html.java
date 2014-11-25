package uk.co.genonline.ldav03.web;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Class to create general HTML markup used in the app.  Not meant to be comprehensive, I will add to it as I go.
 */
public class Html {
    Logger logger = Logger.getLogger("");
    String newline = System.getProperty("line.separator");

    public String constructHtmlTag(String tag, String clazz, String id, String otherAttributes, String content) {
        logger.trace(String.format("constructHtmlTag: tag:%s, class:%s, id:%s, otherAttributes:%s, size of content:%d",
                tag, clazz, id, otherAttributes, content.length()));
        String html;

        html = "<" + tag;
        html += clazz.equals("") ? "" : " class=\"" + clazz + "\"";
        html += id.equals("") ? "" : " id=\"" + id + "\"";
        html += otherAttributes.equals("") ? "" : " " + otherAttributes;
        html += ">" + newline;
        html += content + newline;
        html += "</" + tag + ">" + newline;

        logger.trace(String.format("Html:constructHtmlTag: Html=%n----------%n%s%n----------%n", html));
        return html;
    }

    public String constructAnchorElement(String clazz, String id, String href, String content) {
        return constructHtmlTag("a", clazz, id, String.format("href=\"%s\"", href), content);
    }

    public String constructLiElement(String clazz, String id, String content) {
        return constructHtmlTag("li", clazz, id, "", content);
    }

    public String constructUlElement(String clazz, String id, String content) {
        return constructHtmlTag("ul", clazz, id, "", content);
    }

    public String constructImgElement(String clazz, String id, String src, String content) {
        String srcHtml = (src == null || src.isEmpty()) ? "" : " src=\"" + src + "\"";
        return constructHtmlTag("img", clazz, id, srcHtml, content);
    }

    // ToDo: Add logic for alternate, first and last class values

    /**
     * Create an unordered list from a set of content values to be contained within each LI element of the list.
     *
     * Includes ooptional constructs to aid in styling by use of classes to indicate:
     * - odd and even marking of LI elements to allow alternate styling.
     * - first and last marking of LI elements.
     * - this element (flagged by the name from linkDataList: To allow supression of a live link for the current page.
     *
     *
     * @param ulClass
     * @param ulId
     * @param liClass
     * @param firstClass
     * @param lastClass
     * @param evenOddClass
     * @param liContentValues
     * @return
     */
    public String constructUlElement(String ulClass,
                                     String ulId,
                                     String liClass,
                                     boolean firstClass,
                                     boolean lastClass,
                                     boolean evenOddClass,
                                     List<String> liContentValues) {
        String liHtmlStrings = "";
        boolean first = true;
        boolean odd = true;
        Iterator<String> iterator = liContentValues.iterator();

        while (iterator.hasNext()) {
            String nextContentValue = iterator.next();
            logger.trace(String.format("constructUlElement: Processing content %s", nextContentValue));
            logger.trace(String.format("Odd=%b, first=%b, hasNext=%b", odd, first, iterator.hasNext()));
            String classString = liClass;

            // Logic to set class to indicate first, last, odd, even

            if (first) classString = addAttribute(classString, "first");
            if (!iterator.hasNext()) classString = addAttribute(classString, "last");
            if (odd) {
                classString = addAttribute(classString, "odd");
            } else {
                classString = addAttribute(classString, "even");
            }
            odd = !odd;
            first = false;
            liHtmlStrings += constructLiElement(classString, "", nextContentValue) + newline;
        }
        return constructUlElement(ulClass, ulId, liHtmlStrings);
    }

    /**
     *
     * Generates a Navigation Link Bar (UL/LI/A elements) where the base URL is the same for all links (siblings).
     *
     * @param baseUrl String containing the base url to be used to generate hrefs within the anchor elements of the navbar
     * @param ulClazz
     * @param ulId
     * @param linkDataList
     * @param thisName
     * @return
     */
    public String constructNavBar(String baseUrl, String ulClazz, String ulId, Collection<LinkData> linkDataList, String thisName) {
        logger.trace(String.format("ConstructNavBar, baseUrl:%s, ulClazz:%s, ulId:%s, %d elements, current page:%s",
                baseUrl, ulClazz, ulId, linkDataList.size(), thisName));
        List<String> liValues = new ArrayList<String>();
        for (LinkData linkData : linkDataList) {
            logger.trace(String.format("Processing linkData element %s", linkData));
            String name = linkData.getName();
            String anchorElement = "";
            boolean nullLinkData = false; // Used to indicate if not enough data to construct anchor element

            name = (name == null ? "" : name);
            if ("".equals(name)) {
                logger.warn(String.format("constructNavBar: name blank so can't create link"));
                nullLinkData = true;
            } else {
                if (name.equals(thisName)) {
                    logger.trace(String.format("Construct nav bar: Add link for current or blank page (%s) so make anchor empty", name));
                    anchorElement = linkData.getShortName();
                } else {
                    logger.trace(String.format("Construct nav bar: Add link for different page (%s) so generate href", name));
                    anchorElement = constructAnchorElement("", "", linkData.getHref(), linkData.getShortName());
                }
            }
            if (!nullLinkData) {
                logger.trace(String.format("Adding linkData anchor element to list - %s", anchorElement));
                liValues.add(anchorElement);
            }
        }
        return constructUlElement(ulClazz, ulId, "", true, true, true, liValues);
    }

    /**
     * Utility method to add an attibute (e.g. class) to a string containing current attributes.  Main reason for this
     * is to avoid having to repeat logic for whether to include a space or not.
     *
     * @param attributes
     * @param newAttribute
     * @return
     */
    private String addAttribute(String attributes, String newAttribute) {
        String inputAttributes = attributes == null ? "" : attributes;
        String output = inputAttributes + ("".equals(inputAttributes) ? "" : " ") + newAttribute;
        logger.trace(String.format("addAttribute: Input:%s, New:%s : Output:%s", attributes, newAttribute, output));
        return output;
    }
}
