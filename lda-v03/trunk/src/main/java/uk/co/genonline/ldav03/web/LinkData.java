package uk.co.genonline.ldav03.web;

import uk.co.genonline.ldav03.controller.UrlMapping;

import java.io.File;

/**
 * Carries out common processing required for all entities to create HTML strings for references to a given entity.
 *
 * Represents three different descriptive strings for a record: name, shortName, longName.  The name is used as the
 * reference within an anchor element, the shortName is used as the displayed text for an anchor element, and the
 * longName is used for the Title of a page (or div) relating to a record.
 *
 * If the longName doesn't need to be different from the shortName, it can be left blank in the database, and this
 * class will return the appropriate value.  Similarly, if the shortName is blank (which shouldn't really happen) then
 * the name will be used for shortName.
 *
 * This class is immutable.
 *
 * ToDo: Make sure the class is properly implemented as an immutable class.
 */
public final class LinkData {
    /**
     * The string used within the href attribute of a link to refer to this specific page.
     */
    private String name;

    /**
     * Refers to the string to use as the text for the link.
     */
    private String shortName;

    /**
     * Strictly speaking not required for links.  Included as there is a relationship between the three variants of name.
     * ToDo: Re-visit whether linkData should include longName or not.
     */
    private String longName;

    /**
     * Used to calculate HTML for anchor element.
     */
    private String baseUrl;

    /**
     * Need a constructor with Objects in to avoid unchecked class cast in code which reads from database and
     * then assigns Objects to Strings.
     *
     * @param name
     * @param shortName
     * @param longName
     * @param baseUrl
     */
    public LinkData(Object name, Object shortName, Object longName, Object baseUrl) {
        this.name = (String) name;
        this.shortName = (String) shortName;
        this.longName = (String) longName;
        this.baseUrl = (String) baseUrl;
    }

    public String getName() {
        return name;
    }


    public String getShortName() {
        if (!"".equals(shortName)) {
            return shortName;
        } else {
            return getName();
        }
    }

    public String getLongName() {
        if (!"".equals(longName)) {
            return longName;
        } else {
            return getShortName();
        }
    }

    /**
     * Generates an HTML anchor element using name to complete href and short name as link text
     *
     * @return String containing HTML for anchorElement
     */
    public String getHref() {
        return getBaseUrl() + UrlMapping.VIEW_URL_MAPPING + File.separator + getName();
    }

    public String toString() {
        return String.format("name:%s, shortName:%s, longName:%s", getName(), getShortName(), getLongName());
    }

    public String getBaseUrl() {
        return baseUrl;
    }
}
