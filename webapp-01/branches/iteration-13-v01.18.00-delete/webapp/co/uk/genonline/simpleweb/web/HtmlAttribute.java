package co.uk.genonline.simpleweb.web.gallery;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Created by thomassecondary on 15/04/16.
 *
 * Represents the multiple values of an HTML attribute and provides methods to manage them.
 */
public class HtmlAttribute {
    protected String attributeName;
    protected List<String> attributeValues = new ArrayList<String>();

    /**
     * Constructor which assigns the name of the attribute but no values
     *
     * @param attributeName
     */
    public HtmlAttribute(String attributeName) {
        this.attributeName = attributeName;
    }

    /**
     * Constructor which sets the attribute name and values from an HTML style attribute string.
     *
     * @param attributeName
     * @param attributeValues
     */
    public HtmlAttribute(String attributeName, String attributeValues) {
        this.attributeName = attributeName;
        this.attributeValues = parseHtmlAttributeString(attributeValues);
    }

    /**
     * Constructor which sets the attribute name and values from a list of strings, one for each value
     *
     * @param attributeName
     * @param attributeValues
     */
    public HtmlAttribute(String attributeName, List<String> attributeValues) {
        this.attributeName = attributeName;
        this.attributeValues = attributeValues;
    }

    public String getAttributeName() {
        return attributeName;
    }

    public List<String> getAttributeValues() {
        return attributeValues;
    }

    /**
     * Return a string which represents the correct HTML to set the given attribute name to the current values.
     *
     * For example, If the attribute is "Class" and the values are "myTable" and "leftAligned", the output will be:
     *
     *      "class 'myTable leftAligned'"
     *
     * Note: This class has no knowledge of the semantics and rules about attributes so won't stop the user from
     *       assigning multiple values to an attribute which should only take one.
     *
     * @return
     */
    public String getHtmlAttributeString() {

        if (getAttributeName() == null || getAttributeName().equals("")) {
            return "";
        } else {
            String html = getAttributeName() + " '";

            Iterator<String> valuesIterator = attributeValues.iterator();

            while (valuesIterator.hasNext()) {
                html += valuesIterator.next();
                if (valuesIterator.hasNext()) {
                    html += " ";
                }
            }
            return html;
        }
    }

    public void addValue(String value) {
        attributeValues.add(value);
    }

    /**
     * Take an HTML style attributes string and parses it, separating out the values and placing them into a list of strings.
     *
     * @return
     */
    private List<String> parseHtmlAttributeString(String htmlAttributeString) {
        String[] splitArray = htmlAttributeString.split(" +");
        return Arrays.asList(splitArray);
    }
}
