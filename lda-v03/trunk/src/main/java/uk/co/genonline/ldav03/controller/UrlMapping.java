package uk.co.genonline.ldav03.controller;

/**
 * Created by thomassecondary on 04/11/2014.
 */
public class UrlMapping {
    private UrlMapping() {}

    /**
     * Define constants to help centralise management of URLs.  Not a perfect solution but better than naked String
     * constants everywhere.
     *
     * Approach taken is:
     * - URLs will have the general form (/{entity}/{action}/{entityKey}
     *   e.g. To view a Mistress page - /mistress/view/lucina
     *
     * - I will have a constant for each entity
     * - I will have a constant for each action
     * - I will see how it goes and come back if this isn't simple enough.
     */

    public static final String INDEX_PAGE_URL = "index";

    public static final String MISTRESS_CLASS_URL_MAPPING = "mistress";
    public static final String TESTIMONIAL_CLASS_URL_MAPPING = "testimonial";
    public static final String CHAMBER_CLASS_URL_MAPPING = "chamber";
    public static final String LINKS_CLASS_URL_MAPPING = "links";

    public static final String VIEW_URL_MAPPING = "view";
    public static final String ADD_URL_MAPPING = "add";

    public static final String LINK_BANNER_BASE_URL = "images/linkbanners";

    public static final String MISTRESS_HOME_PAGE_URL = "mistressHome";
    public static final String MISTRESS_HOME_PAGE_VALUE = "lucina";
    public static final String CHAMBERS_HOME_PAGE_URL = "chamberHome";
    public static final String CHAMBERS_HOME_PAGE_VALUE = "facilities";
    public static final String TESTIMONIALS_HOME_PAGE_URL = "testimonialsHome";
    public static final String TESTIMONIALS_HOME_PAGE_VALUE = "thomas-01";
    public static final String LINKS_HOME_PAGE_URL = "linksHome";
    public static final String LINKS_HOME_PAGE_VALUE = "normal";

}
