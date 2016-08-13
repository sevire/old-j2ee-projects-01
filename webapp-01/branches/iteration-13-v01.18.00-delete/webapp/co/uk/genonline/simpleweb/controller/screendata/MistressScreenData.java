package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.configuration.configitems.BlogEnabled;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.controller.screendata.displaybeans.ScreenDataBean;
import co.uk.genonline.simpleweb.controller.screendata.displaybeans.ScreenGalleryBean;
import co.uk.genonline.simpleweb.controller.screendata.displaybeans.ScreenHeaderBean;
import co.uk.genonline.simpleweb.controller.screendata.displaybeans.ScreenMenuBean;
import co.uk.genonline.simpleweb.model.bean.ScreensEntityDecorator;
import co.uk.genonline.simpleweb.model.bean.ScreensManager;
import co.uk.genonline.simpleweb.web.WebHelper;
import co.uk.genonline.simpleweb.web.gallery.Gallery;
import co.uk.genonline.simpleweb.web.gallery.GalleryManagerDefault;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private ScreenDataBean screenData = new ScreenDataBean();
    private ScreenHeaderBean screenHeader = new ScreenHeaderBean();
    private ScreenMenuBean screenMenus = new ScreenMenuBean();

    private ScreenGalleryBean screenGallery = new ScreenGalleryBean();

    protected HttpServletResponse response;
    protected HttpServletRequest request;

    // Properties used by most methods to access data etc.
    protected ScreensManager screensManager;
    protected ServletContext context;
    protected Configuration configuration;
    protected RequestStatus status;
    protected WebHelper webHelper; // WebHelper class helps with formatting HTML elements to be passed to page
    protected ScreensEntityDecorator screenRecord; // Populated

    // Properties used for containing output data (HTML etc)
    protected WebLogger logger = new WebLogger();

    protected String screenName; // Used to store screenName extracted from ScreenEntity object within Session.

    public MistressScreenData(String screenType) {
        super(screenType);
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

                setScreenData();
                setScreenHeader();
                setScreenMenus();
                setScreenGallery();

                return new RequestResult(request, getJSPname(), false);
            } else {
                logger.info(String.format("Screen disabled, treating like non-existent page <%s>", screenRecord.getName()));
                response.setStatus(404);
                return new RequestResult(request, "error.jsp", false);
            }
        }
    }

    public ScreenDataBean getScreenData() {
        return screenData;
    }

    public void setScreenData() {
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
        List<String> headerImages = webHelper.selectRandomImage("site_images/header_images", 2);
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
}
