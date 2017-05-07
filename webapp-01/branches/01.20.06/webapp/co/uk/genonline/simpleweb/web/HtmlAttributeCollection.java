package co.uk.genonline.simpleweb.web;

import co.uk.genonline.simpleweb.web.gallery.HtmlAttribute;

import java.util.*;

/**
 * Created by thomassecondary on 10/04/16.
 *
 * Class to manage the attributes which I will need in HTML elements.  Works with gagawa Html helper class.
 *
 * Note that while I am using a Map which seems appropriate, a given attribute can have more than one value so I have implemented
 * this by using HtmlAttribute for the type of the values.  This makes things a little more complex but I want it to work properly,
 * particularly as the way I will use it will be for the class attribute typically which often have several values.
 */

public class HtmlAttributeCollection implements Map<String, HtmlAttribute> {
    private Map<String, HtmlAttribute> attributeList = new HashMap<String, HtmlAttribute>();
    private String dummy = "dummy"; // just to see what is different from attributeLink declaration

    public HtmlAttributeCollection(String name, HtmlAttribute attribute) {
        put(name, attribute); // ToDo: Add method to just add single attribute value - e.g. addAttributeValue(String s)
    }

    public String getAttributeHtml(String attributeName) {
        if (!attributeList.containsKey(attributeName)) {
            return "";
        } else {
            HtmlAttribute attribute = attributeList.get(attributeName);
            return attribute.getHtmlAttributeString();
        }
    }

    /**
     * Outputs the attributes as a single string which can be used in an HTML element
     * @return
     */
    public String getAllAttributesHtml() {
        String outputString = "";
        if (size() == 0) {
            return "";
        } else {
            Iterator<String> nameIterator = keySet().iterator();

            while (nameIterator.hasNext()) {
                String attributeName = nameIterator.next();
                outputString += attributeName + " ";
                outputString += getAttributeHtml(attributeName);
                if (nameIterator.hasNext()) {
                    outputString += " ";
                }
            }
        }
        return outputString;
    }

    public int size() {
        return attributeList.size();
    }

    public boolean isEmpty() {
        return attributeList.isEmpty();
    }

    public boolean containsKey(Object o) {
        return attributeList.containsKey(o);
    }

    public boolean containsValue(Object o) {
        return attributeList.containsValue(o);
    }

    public HtmlAttribute get(Object o) {
        return attributeList.get(o);
    }

    public HtmlAttribute put(String s, HtmlAttribute s2) {
        return attributeList.put(s, s2);
    }

    public HtmlAttribute remove(Object o) {
        return attributeList.remove(o);
    }

    public void putAll(Map map) {
        attributeList.putAll(map);
    }

    public void clear() {
        attributeList.clear();
    }

    public Set<String> keySet() {
        return attributeList.keySet();
    }

    public Collection values() {
        return attributeList.values();
    }

    public Set<Entry<String, HtmlAttribute>> entrySet() {
        return attributeList.entrySet();
    }


}
