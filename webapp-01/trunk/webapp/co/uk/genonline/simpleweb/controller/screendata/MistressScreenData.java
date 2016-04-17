package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.configuration.configitems.BlogEnabled;
import co.uk.genonline.simpleweb.configuration.configitems.HomePage;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.controller.actions.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.web.WebHelper;
import co.uk.genonline.simpleweb.web.gallery.Gallery;
import co.uk.genonline.simpleweb.web.gallery.GalleryManagerDefault;
import com.petebevin.markdown.MarkdownProcessor;

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
public class MistressScreenData implements ScreenData {

    protected HttpServletResponse response;
    protected HttpServletRequest request;

    // Properties used by most methods to access data etc.
    protected ServletContext context;
    protected Configuration configuration;
    protected RequestStatus status;
    protected WebHelper webHelper; // WebHelper class helps with formatting HTML elements to be passed to page
    protected ScreensEntity screenRecord;

    // Properties used for containing output data (HTML etc)
    protected WebLogger logger = new WebLogger();
    private String chambersLinkBar;
    private String lucinaLinkBar;
    private String mistressLinkBar;
    private String testimonialLinkBar;
    private String galleryLinkBar;
    private String mistressPageLink;
    private String homePageLink;
    private String maxThumbnailWidth;
    private String maxThumbnailHeight;
    private String blogLink;
    private String galleryHtml;
    private ScreensEntity screen;
    private String headerImageLeft;
    private String headerImageRight;

    protected void init(HttpServletRequest request,
                              HttpServletResponse response,
                              ScreenBeanManager screenBeanManager,
                              SessionData sessionData) {
        this.request = request;
        this.response = response;

        context = request.getServletContext();
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
        setScreen(sessionData.getScreen());

        /* Check that screen name has a value.  If not populate with home page screen name. */
        if (screen.getName() == null || screen.getName().equals("")) {
            screen.setName(((HomePage) configuration.getConfigurationItem("homePage")).getStringValue());
        }
        logger.info("view: screen is " + getScreen().getName());

        screenRecord = screenBeanManager.getScreen(screen);  // openSession() Invocation #2

    }

    /**
     * Part of refactoring.
     *
     * Set up main text content field for this screen.
     */
    protected void setScreenContents() {
        /* Set up Screen contents */
        MarkdownProcessor markdownDecoder = new MarkdownProcessor();

        logger.debug("About to parse page with Markdown");
        String pageText = screenRecord.getScreenContents();

        logger.debug("Markdown Input text is " + pageText.substring(0, Math.min(39, pageText.length())) + "...");
        String HTML = markdownDecoder.markdown(pageText);

        logger.debug("Markdown Output HTML is " + HTML.substring(0, Math.min(39, HTML.length())) + "...");
        screen.setScreenContents(HTML);
    }

    /**
     * Note: Method created as part of refactoring so may need some fine tuning.  Just trying to group related activities
     * together so I can create variants more easily without copying code.
     *
     * Sets non content data which is used on page, such as title, metadescription etc.
     */
    protected void setPageData() {
        screen.setMetaDescription(screenRecord.getMetaDescription());
        screen.setScreenTitleLong(screenRecord.getScreenTitleLong());
        screen.setScreenTitleShort(screenRecord.getScreenTitleShort());
    }

    protected void setBlogEnabledFlag() {
        boolean blogEnabled = ((BlogEnabled) configuration.getConfigurationItem("blogEnabled")).get();
        logger.debug(String.format("Value of 'blogEnabled' is %b", blogEnabled));
        if (blogEnabled) {
            setBlogLink(webHelper.generateBlogLink());
        } else {
            setBlogLink(null);
        }
    }

    protected void setGalleryData() {
        if (screenRecord.getGalleryFlag()) {
            setMaxThumbnailWidth(((configuration.getConfigurationItem("maxThumbnailWidth"))).getStringValue());
            setMaxThumbnailHeight(((configuration.getConfigurationItem("maxThumbnailHeight"))).getStringValue());

            logger.info("About to create gallery for page <%s>", screenRecord.getName());
            GalleryManagerDefault manager = (GalleryManagerDefault) request.getServletContext().getAttribute("Galleries");
            logger.debug("manager = " + manager);

            Gallery gallery = manager.getGallery(screenRecord.getName());
            if (gallery == null) {
                logger.error("Couldn't retrieve gallery for <%s>, setting blank gallery", screenRecord.getName());
            } else {
                setGalleryHtml(gallery.getHtml(false));
            }
        } else {
            logger.trace("This page is not a gallery: " + screenRecord.getName());
            setGalleryHtml("");
        }
    }

    protected void setHeaderRandomImages() {
        List<String> headerImages = webHelper.selectRandomImage("site_images/header_images", 2);

        setHeaderImageLeft(headerImages.get(0));
        setHeaderImageRight(headerImages.get(1));
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
                                       ScreenBeanManager screenBeanManager,
                                       SessionData data
    ) {
        init(request, response, screenBeanManager, data);

        if (screenRecord == null) {
            logger.warn(String.format("View page: Couldn't retrieve page <%s>", getScreen().getName()));
            response.setStatus(404);
            return new RequestResult(request, "error.jsp", false);
        } else {
            if (screenRecord.getEnabledFlag()) {

                setScreenContents();
                setPageData();
                setBlogEnabledFlag();

                setCategoryLinkBar("Lucina");
                setCategoryLinkBar("Chambers");
                setCategoryLinkBar("Mistress");
                setCategoryLinkBar("Testimonial");
                setCategoryLinkBar("Gallery");

                    // I don't think we need the mistress page link for recent page formats but needed to support older ones
                setMistressPageLink(webHelper.getScreenLink("mistresses"));
                setHomePageLink(webHelper.generateHomeLink());

                setGalleryData();

                setHeaderRandomImages();
                return new RequestResult(request, getJSPname(), false);
            } else {
                logger.info(String.format("Screen disabled, treating like non-existent page <%s>", screenRecord.getName()));
                response.setStatus(404);
                return new RequestResult(request, "error.jsp", false);
            }
        }
    }

    public String getHomePageLink() {
        return homePageLink;
    }

    public void setHomePageLink(String homePageLink) {
        this.homePageLink = homePageLink;
    }

    public String getMaxThumbnailWidth() {
        return maxThumbnailWidth;
    }

    public void setMaxThumbnailWidth(String maxThumbnailWidth) {
        this.maxThumbnailWidth = maxThumbnailWidth;
    }

    public String getMaxThumbnailHeight() {
        return maxThumbnailHeight;
    }

    public void setMaxThumbnailHeight(String maxThumbnailHeight) {
        this.maxThumbnailHeight = maxThumbnailHeight;
    }


    public String getJSPname() {
        return "";  // Need to extend for each jsp used
    }

    public void setChambersLinkBar(String linkBar) {
        this.chambersLinkBar = linkBar;
    }

    public void setCategoryLinkBar(String categoryName) {
        String linkBar = webHelper.generateLinkBarCategory(categoryName);
        if (categoryName.equals("Mistress")) {
            mistressLinkBar = linkBar;
        } else if (categoryName.equals("Lucina")) {
            lucinaLinkBar = linkBar;
        } else if (categoryName.equals("Chambers")) {
            chambersLinkBar = linkBar;
        } else if (categoryName.equals("Testimonial")) {
            testimonialLinkBar = linkBar;
        } else if (categoryName.equals("Gallery")) {
            galleryLinkBar = linkBar;
        }
    }

    public void setMistressPageLink(String link) {
        this.mistressPageLink = link;
    }

    public String getLucinaLinkBar() {
        return lucinaLinkBar;
    }

    public String getChambersLinkBar() {
        return chambersLinkBar;
    }

    public String getMistressLinkBar() {
        return mistressLinkBar;
    }

    public String getTestimonialLinkBar() {
        return testimonialLinkBar;
    }

    public String getGalleryLinkBar() {
        return galleryLinkBar;
    }

    public String getMistressPageLink() {
        return mistressPageLink;
    }

    public String getBlogLink() {
        return blogLink;
    }

    public void setBlogLink(String blogLink) {
        this.blogLink = blogLink;
    }

    public void setGalleryHtml(String galleryHtml) {
        this.galleryHtml = galleryHtml;
    }

    public String getGalleryHtml() {
        return galleryHtml;
    }

    public ScreensEntity getScreen() {
        return screen;
    }

    public void setScreen(ScreensEntity screen) {
        this.screen = screen;
    }

    public String getHeaderImageLeft() {
        return headerImageLeft;
    }

    public void setHeaderImageLeft(String headerImageLeft) {
        this.headerImageLeft = headerImageLeft;
    }

    public String getHeaderImageRight() {
        return headerImageRight;
    }

    public void setHeaderImageRight(String headerImageRight) {
        this.headerImageRight = headerImageRight;
    }

}
