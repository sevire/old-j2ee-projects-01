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

    public static final String MISTRESS_CLASS_URL_MAPPING = "/mistress";
    public static final String TESTIMONIAL_CLASS_URL_MAPPING = "/testimonial";
    public static final String CHAMBER_CLASS_URL_MAPPING = "/chamber";

    public static final String VIEW_URL_MAPPING = "/view";
    public static final String ADD_URL_MAPPING = "/add";
}
