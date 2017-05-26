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
    boolean includeMistressTable;


    public MistressScreenDataFlags(
            boolean includeSiteData,
            boolean includeScreenDetails,
            boolean includeScreenHeader,
            boolean includeScreenMenus,
            boolean includeScreenGallery,
            boolean includeMistressTable,
            boolean includeLinksData) {
        this.includeSiteData = includeSiteData;
        this.includeScreenDetails = includeScreenDetails;
        this.includeScreenHeader = includeScreenHeader;
        this.includeScreenMenus = includeScreenMenus;
        this.includeScreenGallery = includeScreenGallery;
        this.includeMistressTable = includeMistressTable;
        this.includeLinksData = includeLinksData;
    }
}
