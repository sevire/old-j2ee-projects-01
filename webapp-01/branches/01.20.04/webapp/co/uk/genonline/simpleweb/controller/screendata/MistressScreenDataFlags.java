package co.uk.genonline.simpleweb.controller.screendata;

/**
 * Created by thomassecondary on 18/03/2017.
 */
public class MistressScreenDataFlags {
    boolean includeSiteData;
    boolean includeScreenDetails;
    boolean includeScreenHeader;
    boolean includeScreenMenus;
    boolean includeScreenGallery;
    boolean includeLinksData;


    public MistressScreenDataFlags(
            boolean includeSiteData,
            boolean includeScreenDetails,
            boolean includeScreenHeader,
            boolean includeScreenMenus,
            boolean includeScreenGallery,
            boolean includeLinksData) {
        this.includeSiteData = includeSiteData;
        this.includeScreenDetails = includeScreenDetails;
        this.includeScreenHeader = includeScreenHeader;
        this.includeScreenMenus = includeScreenMenus;
        this.includeScreenGallery = includeScreenGallery;
        this.includeLinksData = includeLinksData;
    }
}
