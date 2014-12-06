package uk.co.genonline.ldav03.web;

import java.util.ArrayList;
import java.util.List;

import static uk.co.genonline.ldav03.controller.UrlMapping.*;

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

        List<String> liValues = new ArrayList<String>();

        liValues.add(html.constructAnchorElement("","",
                "/" + MISTRESS_CLASS_URL_MAPPING + "/" + VIEW_URL_MAPPING + "/" + MISTRESS_HOME_PAGE_URL,
                html.constructImgElement("", "", "/images/icons/links/CategoryLogo-Home.png", html.constructHtmlTag("h1", "", "", "", "Home"))
        ));

        liValues.add(html.constructAnchorElement("","",
                "/" + MISTRESS_CLASS_URL_MAPPING + "/" + VIEW_URL_MAPPING + "/" + MISTRESS_HOME_PAGE_URL,
                html.constructImgElement("", "", "/images/icons/links/CategoryLogo-Home.png", html.constructHtmlTag("h1", "", "", "", "Mistresses"))
        ));

        liValues.add(html.constructAnchorElement("","",
                "/" + CHAMBER_CLASS_URL_MAPPING + "/" + VIEW_URL_MAPPING + "/" + CHAMBERS_HOME_PAGE_URL,
                html.constructImgElement("", "", "/images/icons/links/CategoryLogo-Home.png", html.constructHtmlTag("h1", "", "", "", "Chambers"))
        ));

        liValues.add(html.constructAnchorElement("","",
                "/" + TESTIMONIAL_CLASS_URL_MAPPING + "/" + VIEW_URL_MAPPING + "/" + TESTIMONIALS_HOME_PAGE_URL,
                html.constructImgElement("", "", "/images/icons/links/CategoryLogo-Home.png", html.constructHtmlTag("h1", "", "", "", "Testimonials"))
        ));

        liValues.add(html.constructAnchorElement("","",
                "/" + LINKS_CLASS_URL_MAPPING + "/" + VIEW_URL_MAPPING + "/linksHome",
                html.constructImgElement("", "", "/images/icons/links/CategoryLogo-Home.png", html.constructHtmlTag("h1", "", "", "", "Links"))
        ));

        this.topLinkHtml = html.constructUlElement("", "topNavbar", "", true, true, true, liValues);
    }

    public String getTopLinkbar() {
        if (topLinkHtml == null) {
            constructHtml();
        }
        return topLinkHtml;
    }
}
