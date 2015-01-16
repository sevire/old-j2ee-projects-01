package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.configuration.configitems.BlogEnabled;
import co.uk.genonline.simpleweb.configuration.configitems.HomePage;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.RequestStatus;
import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.controller.actions.ActionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.model.bean.ScreenBeanManager;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.web.WebHelper;
import co.uk.genonline.simpleweb.web.gallery.GalleryManager;
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

    private WebLogger logger = new WebLogger();
    private String chambersLinkBar;
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

    /* WebHelper class helps with formatting HTML elements to be passed to page */
    private WebHelper webHelper;

    /**
     * Sets up all data to be passed to jsp file for any screen which has a display type which maps to this class
     * (typically most of the 'Mistress' style pages at the time of writing).  In particular:
     *
     * <ul>
     *     <li>Data for this screen, including screen headings, markdown'ed content.</li>
     *     <li>General data, such as linkbars, home screen link etc</li>
     *     <li>Gallery html if this is a gallery screen</li>
     * </ul>
     * @param request
     * @param response
     * @param screenBeanManager
     * @param data
     * @return
     */
    public RequestResult setScreenData(HttpServletRequest request,
                                       HttpServletResponse response,
                                       ScreenBeanManager screenBeanManager,
                                       ActionData sessionData) {

        ServletContext context = request.getServletContext();
        Configuration configuration = (Configuration)context.getAttribute("configuration");

        /* RequestStatus used to display any errors or status messages on screen. Main use is for admin screens.
         * Not used here
         */
        RequestStatus status = (RequestStatus) request.getSession().getAttribute("requestStatus");
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

        ScreensEntity screenRecord = screenBeanManager.getScreen(screen);

        if (screenRecord == null) {
            logger.warn(String.format("View page: Couldn't retrieve page <%s>", getScreen().getName()));
            response.setStatus(404);
            return new RequestResult(request, "error.jsp", false);
        } else {

            /* Set up Screen contents */
            MarkdownProcessor markdownDecoder = new MarkdownProcessor();

            logger.debug("About to parse page with Markdown");
            String pageText = screenRecord.getScreenContents();

            logger.debug("Markdown Input text is " + pageText.substring(0, Math.min(39, pageText.length()))+"...");
            String HTML = markdownDecoder.markdown(pageText);

            logger.debug("Markdown Output HTML is " + HTML.substring(0, Math.min(39, HTML.length()))+"...");
            getScreen().setScreenContents(HTML);

            /* Set up other Screen related data */
            screen.setMetaDescription(screenRecord.getMetaDescription());
            screen.setScreenTitleLong(screenRecord.getScreenTitleLong());
            screen.setScreenTitleShort(screenRecord.getScreenTitleShort());

            /* Set up other data that JSP will need */
            boolean blogEnabled = ((BlogEnabled)configuration.getConfigurationItem("blogEnabled")).get();
            logger.debug(String.format("Value of 'blogEnabled' is %b", blogEnabled));
            if (blogEnabled) {
                setBlogLink(webHelper.generateBlogLink());
            } else {
                setBlogLink(null);
            }
            setCategoryLinkBar("Chambers");
            setCategoryLinkBar("Mistress");
            setCategoryLinkBar("Testimonial");
            setCategoryLinkBar("Gallery");
            setMistressPageLink(webHelper.getScreenLink("mistresses", screenBeanManager.getShortName("mistresses")));
            setHomePageLink(webHelper.generateHomeLink());
            setMaxThumbnailWidth(((configuration.getConfigurationItem("maxThumbnailWidth"))).getStringValue());
            setMaxThumbnailHeight(((configuration.getConfigurationItem("maxThumbnailHeight"))).getStringValue());

            /* Set up Screen Images for header (this should be in a common place!). For now hard code left
               and right images.  Will replace with random selection later. */

            List<String> headerImages = webHelper.selectRandomImage("site_images/header_images", 2);

            setHeaderImageLeft(headerImages.get(0));
            setHeaderImageRight(headerImages.get(1));

            /* Set up Gallery if appropriate */
            if (screenRecord.getGalleryFlag()) {
                logger.info("About to create gallery for the page");
                GalleryManager manager = (GalleryManager)request.getServletContext().getAttribute("Galleries");
                logger.debug("manager = " + manager);
                setGalleryHtml(((manager.getGallery(screenRecord.getName())).getHTML()));
            } else {
                logger.debug("This page is not a gallery: " + screenRecord.getName());
                setGalleryHtml("");
            }
            if (screenRecord.getEnabledFlag()) {
                // TODO: Move this test earlier to avoid wasted effort on disabled screen.
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
