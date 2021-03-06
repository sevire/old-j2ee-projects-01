package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.configuration.configitems.BlogEnabled;
import co.uk.genonline.simpleweb.configuration.configitems.StaticFileRootFile;
import co.uk.genonline.simpleweb.configuration.configitems.StaticFileRootURL;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.controller.screendata.displaybeans.*;
import co.uk.genonline.simpleweb.model.bean.*;
import co.uk.genonline.simpleweb.monitoring.Collator;
import co.uk.genonline.simpleweb.web.WebHelper;
import co.uk.genonline.simpleweb.web.gallery.Gallery;
import co.uk.genonline.simpleweb.web.gallery.GalleryManagerDefault;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.gagawa.java.elements.*;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 12/10/2013
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class MistressScreenData extends ScreenData {

    // Member variables which will be accessed by JSP via getters and setters

    private SiteDataBean siteData = new SiteDataBean();

    private ScreenDataBean screenData = new ScreenDataBean();
    private ScreenHeaderBean screenHeader = new ScreenHeaderBean();
    private ScreenMenuBean screenMenus = new ScreenMenuBean();
    private ScreenGalleryBean screenGallery = new ScreenGalleryBean();
    private ScreenMistressTableBean screenMistressTable = new ScreenMistressTableBean();
    private LinksDataBean linksData = new LinksDataBean();

    protected HttpServletResponse response;
    protected HttpServletRequest request;

    // Properties used by most methods to access data etc.
    protected ScreensManager screensManager;
    protected ServletContext context;
    protected Configuration configuration;
    protected RequestStatus status;
    protected WebHelper webHelper; // WebHelper class helps with formatting HTML elements to be passed to page
    protected ScreensEntityDecorator screenRecord; // Populated

    // Add flags for which elements appear on this page (part of refactoring to make more generic)
    protected MistressScreenDataFlags mistressScreenDataFlags;

    // Properties used for containing output data (HTML etc)
    protected WebLogger logger = new WebLogger();

    protected String screenName; // Used to store screenName extracted from ScreenEntity object within Session.

    public MistressScreenData(String screenType) {
        super(screenType);
        mistressScreenDataFlags = new MistressScreenDataFlags(
                true,
                true,
                true,
                true,
                true,
                false,
                false
        );
    }

    protected void init(HttpServletRequest request,
                              HttpServletResponse response,
                              ScreensManager screensManager,
                              SessionData sessionData) {
        this.request = request;
        this.response = response;
        this.screensManager = screensManager;

        context = this.request.getServletContext();
        configuration = (Configuration)context.getAttribute("configuration");

        /**
         * RequestStatus used to display any errors or status messages on screen. Main use is for admin screens.
         * Not used here
         */
        status = (RequestStatus) request.getSession().getAttribute("requestStatus");

        status.resetStatusMessage();

        webHelper = new WebHelper(request, response);

        /**
         * Make sure that the Screen member is the same object as that from the session data otherwise will be
         * accessing the wrong one from the JSP.
         */
        this.screenName = sessionData.getScreen() == null ? "" : sessionData.getScreen().getName();

        /* Check that screen name has a value.  If not populate with home page screen name. */
        if (screenName.equals("")) {
            screenName = configuration.getConfigurationItem("homePage").getStringValue();
        }
        logger.info("view: screen is " + screenName);

        screenRecord = new ScreensEntityDecorator(screensManager.getScreen(screenName, false));

    }

    /**
     * Sets up all data to be passed to jsp file for any screen which has a display type which maps to this class
     * (typically most of the 'Mistress' style pages at the time of writing).  In particular:
     *
     * <ul>
     *     <li>Data for this screen, including screen headings, markdown'ed content.</li>
     *     <li>General data, such as linkbars, home screen link etc</li>
     *     <li>Gallery html if this is a gallery screen</li>
     * </ul>
     * @return
     */
    public RequestResult setScreenData(HttpServletRequest request,
                                       HttpServletResponse response,
                                       ScreensManager screensManager,
                                       SessionData data
    ) {
        init(request, response, screensManager, data);

        if (screenRecord == null) {
            logger.warn(String.format("View page: Couldn't retrieve page <%s>", screenName));
            response.setStatus(404);
            return new RequestResult(request, "error.jsp", false);
        } else {
            if (screenRecord.getEnabledFlag()) {

                if (mistressScreenDataFlags.includeSiteData) setSiteData();
                if (mistressScreenDataFlags.includeScreenDetails)setScreenDetailsData();
                if (mistressScreenDataFlags.includeScreenHeader)setScreenHeader();
                if (mistressScreenDataFlags.includeScreenMenus)setScreenMenus();
                if (mistressScreenDataFlags.includeScreenGallery)setScreenGallery();
                if (mistressScreenDataFlags.includeLinksData) setLinksData();
                if (mistressScreenDataFlags.includeMistressTable) setScreenMistressTable();

                return new RequestResult(request, getJSPname(), false);
            } else {
                logger.info(String.format("Screen disabled, treating like non-existent page <%s>", screenRecord.getName()));
                response.setStatus(404);
                return new RequestResult(request, "error.jsp", false);
            }
        }
    }

    public void setSiteData() {
        siteData.setStaticFileRootURLPath(((StaticFileRootURL) (configuration.getConfigurationItem("staticFileRootURL"))).get());
    }

    public SiteDataBean getSiteData() { return siteData; }

    public ScreenDataBean getScreenData() {
        return screenData;
    }

    public void setScreenDetailsData() {
        screenData.setScreensEntityDecorator(screenRecord);
        screenData.setBlogEnabledFlag(((BlogEnabled) configuration.getConfigurationItem("blogEnabled")).get());
        screenData.setBlogLink(webHelper.generateBlogLink());
        screenData.setMistressPageLink(webHelper.getScreenLink("mistresses"));
        screenData.setHomePageLink(webHelper.generateHomeLink());
    }

    public ScreenHeaderBean getScreenHeader() {
        return screenHeader;
    }

    public void setScreenHeader() {
        String headerImagesFullPath = ((StaticFileRootFile) configuration.getConfigurationItem("staticFileRootFile")).get()
                + File.separator + "site-images/left-right-header-images";
        List<String> headerImages = webHelper.selectRandomImage(headerImagesFullPath, 2);
        screenHeader.setHeaderImageLeft(headerImages.get(0));
        screenHeader.setHeaderImageRight(headerImages.get(1));
    }

    public ScreenMenuBean getScreenMenus() {
        return screenMenus;
    }

    public void setScreenMenus() {
        screenMenus.setMenu("lucinaLinkBar", webHelper.generateLinkBarCategory("Lucina"));
        screenMenus.setMenu("chambersLinkBar", webHelper.generateLinkBarCategory("Chambers"));
        screenMenus.setMenu("mistressLinkBar", webHelper.generateLinkBarCategory("Mistress"));
        screenMenus.setMenu("testimonialLinkBar", webHelper.generateLinkBarCategory("Testimonial"));
        screenMenus.setMenu("galleryLinkBar", webHelper.generateLinkBarCategory("Gallery"));
    }

    public ScreenGalleryBean getScreenGallery() {
        return screenGallery;
    }

    public void setScreenGallery() {
        if (screenRecord.getGalleryFlag()) {
            screenGallery.setMaxThumbnailWidth(((configuration.getConfigurationItem("maxThumbnailWidth"))).getStringValue());
            screenGallery.setMaxThumbnailHeight(((configuration.getConfigurationItem("maxThumbnailHeight"))).getStringValue());

            logger.info("About to create gallery for page <%s>", screenRecord.getName());
            GalleryManagerDefault manager = (GalleryManagerDefault) request.getServletContext().getAttribute("Galleries");
            logger.debug("manager = " + manager);

            Gallery gallery = manager.getGallery(screenRecord.getName());
            if (gallery == null) {
                logger.error("Couldn't retrieve gallery for <%s>, setting blank gallery", screenRecord.getName());
            } else {
                screenGallery.setGalleryData(gallery.getHtml(false));
            }
        } else {
            logger.trace("This page is not a gallery: " + screenRecord.getName());
            screenGallery.setGalleryData("");
        }
    }

    public ScreenMistressTableBean getScreenMistressTable() {
        return screenMistressTable;
    }

    public void setScreenMistressTable() {
        List<ScreensEntity> mistressScreens = screensManager.getScreensByType("Mistress", false);

        Table tableElement = new Table();
        tableElement.setCSSClass("mistressLinks");
        for (ScreensEntity screen : mistressScreens) {
            Tr tableRow = new Tr();

            Td imageCell = new Td();
            imageCell.setCSSClass("imageCell");

            Td linkCell = new Td();
            linkCell.setCSSClass("linkCell");

            Img imageElement = new Img(screen.getName(), "site_images/" + screen.getName() + ".jpg");
            imageCell.appendChild(imageElement);

            A mistressLink = new A("view?screen="+screen.getName());
            mistressLink.appendText(screen.getScreenTitleShort());
            mistressLink.setCSSClass("mistressLink");
            linkCell.appendChild(mistressLink);

            tableRow.appendChild(imageCell);
            tableRow.appendChild(linkCell);

            tableElement.appendChild(tableRow);
        }
        String Html = tableElement.write();
        screenMistressTable.setMistressTable(Html);
    }


    /**
     * Note: Getter isn't called within the app, but from the JSP so don't delete!  This is true for all the data
     * element getters.
     *
     * @return
     */
    public LinksDataBean getLinksData() {
        return linksData;
    }

    public void setLinksData() {
        ServletContext context = request.getServletContext();
        SessionFactory factory = (SessionFactory) context.getAttribute("sessionFactory");
        LinksConfiguration linksConfiguration = configuration.getLinksConfiguration();
        LinksManager linksManager = new LinksManagerNonCaching(factory, linksConfiguration, (Collator)request.getServletContext().getAttribute("monitoringCollator"));
        List<LinksEntityExtended> liveLinks = linksManager.getLiveBannerLinks();

        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        linksData.setJsonString(gson.toJson(liveLinks));
        linksData.setLinksRoot(linksConfiguration.getLinksRoot());
    }

}
