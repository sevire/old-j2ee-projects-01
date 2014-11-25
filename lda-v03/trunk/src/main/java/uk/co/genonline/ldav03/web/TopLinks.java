package uk.co.genonline.ldav03.web;

import uk.co.genonline.ldav03.controller.UrlMapping;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Just calculates and returns a top level linkbar.
 * Not sure whether this is the right way to do this.  Can't think of a better way.
 * ToDo: Make this more sophisticated.  Lot's of hard coding in this initial implementation.
 */

public class TopLinks {
    String topLinkHtml;

    /**
     * Do all the hard work up front (eager rather than lazy), so when get method called, just return
     * the string which has already been set up.
     */
    public TopLinks() {
        constructHtml();
    }

    private void constructHtml() {
        // Construct the top nav bar used on all pages

        Html html = new Html();
        String htmlString = "";

        Collection<LinkData> linkData = new ArrayList<LinkData>();

        linkData.add(new LinkData("mistressHome", "Mistresses", "", UrlMapping.MISTRESS_CLASS_URL_MAPPING));
        linkData.add(new LinkData("chamberHome", "Chambers", "", UrlMapping.CHAMBER_CLASS_URL_MAPPING));
        linkData.add(new LinkData("testimonialHome", "Testimonials", "", UrlMapping.TESTIMONIAL_CLASS_URL_MAPPING));
        linkData.add(new LinkData("lucina", "Home", "", UrlMapping.MISTRESS_CLASS_URL_MAPPING));

        this.topLinkHtml = html.constructNavBar("topLinks", "", "topLevelNav", linkData, "");
    }

    public String getTopLinkbar() {
        if (topLinkHtml == null) {
            constructHtml();
        }
        return topLinkHtml;
    }
}
