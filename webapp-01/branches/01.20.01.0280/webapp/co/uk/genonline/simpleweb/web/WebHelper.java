package co.uk.genonline.simpleweb.web;

import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.model.bean.*;
import co.uk.genonline.simpleweb.web.gallery.ImageFileFilter;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 10/06/2012
 * Time: 09:36
 *
 * Contains a number of utility methods for generating elements of a web page for the website.
 */
public class WebHelper {
    WebLogger logger;
    SessionFactory factory;
    HttpServletRequest request;
    HttpServletResponse response;
    Configuration configuration;

    public WebHelper(HttpServletRequest request, HttpServletResponse response) {

        logger = new WebLogger(request);
//        logger.setLevel(Level.toLevel(request.getServletContext().getInitParameter("loggingLevel")));
        logger.info("Logger initiated");
        this.request = request;
        this.response = response;
        this.factory = (SessionFactory)request.getServletContext().getAttribute("sessionFactory");
        this.configuration = (Configuration)(request.getServletContext().getAttribute("configuration"));
    }

    public String generateScreenLink(String name, String screenTitleShort) {
        return String.format("<a href='view?screen=%s'>%s</a>",name, screenTitleShort);
    }

    public String generateLinkBarItem(String name, String screenTitleShort) {
        return String.format("<li class='headerLink'>%s</li>",
                generateScreenLink(name, screenTitleShort));
    }

    public String generateLinkBarCategory(String category) {
        // ToDo: WebHelper - remove use of ScreenBeanManager
        ScreensManager screensManager = new ScreensManagerNonCaching(factory);
        List<ScreensEntity> screens = screensManager.getScreensByType(category, ScreensSortType.LINKBAR, false);
        String html = "";
        for (ScreensEntity screen : screens) {
            html += generateLinkBarItem(screen.getName(), screen.getScreenTitleShort()) ;
        }
        html = "<ul>" + html + "</ul>";
        return html;
    }

    public String generateHomeLink() {
        String homePage = configuration.getConfigurationItem("homePage").getStringValue();
        logger.info(String.format("Generating home page = <%s>", homePage));
        return String.format("<a href='view?screen=%s'>Home</a>", homePage);
    }

    public String generateBlogLink() {
        return "<a href='http://manchestermistresslucina.blogspot.co.uk/'>Blog</a>";
    }

/*
    public String getHomePage() {
        return ((HomePage)(configuration.getConfigurationItem("homePage"))).get();
    }

*/
    public String getScreenLink(String screenName) {
        ScreensEntity screenData = new ScreensEntity();
        getScreenIntoBean(screenData, screenName);
        return String.format("<a href='view?screen=%s'>%s</a>", screenName, screenData.getScreenTitleShort());
    }

    /**
     * Finds all the records in the database with a given category and creates a list of WebLinks which can then be used
     * to create a linkbar or similar.
     *
     * @param
     * @return
     */
    // ToDo: WebHelper getLinkRecords - Confirm whether can delete this.
/*
    public List<WebLink> getLinkRecords(String category) {
        ScreenBeanManager beanManager = new ScreenBeanManager(factory);
        java.util.List pages = beanManager.getCategoryScreens(category);
        if (pages.size() > 0) {
            List<WebLink> linkList = new ArrayList<WebLink>();
            for (Object o : pages) {
                ScreensEntity screen = (ScreensEntity) o;
                linkList.add(new WebLink(String.format("view?screen=%s", screen.getName()), screen.getScreenTitleShort(), null));
            }
            return linkList;
        } else {
            return null;
        }
    }
*/

    public void getScreenIntoBean(ScreensEntity screen, String screenName) {
        if (screen != null && screenName != null) {
            Session session;
            Transaction tx = null;
            try {
                session = factory.openSession();
                tx = session.beginTransaction();
                Criteria criteria = session.createCriteria(ScreensEntity.class).add(Restrictions.eq("name", screenName));
                ScreensEntity dbBean = (ScreensEntity) criteria.uniqueResult();
                tx.commit();

                if (dbBean != null) {
                    screen.setName(dbBean.getName());
                    screen.setSortKey(dbBean.getSortKey());
                    screen.setEnabledFlag(dbBean.getEnabledFlag());
                    screen.setGalleryFlag(dbBean.getGalleryFlag());
                    screen.setScreenContents(dbBean.getScreenContents());
                    screen.setMetaDescription(dbBean.getMetaDescription());
                    screen.setScreenTitleLong(dbBean.getScreenTitleLong());
                    screen.setScreenTitleShort(dbBean.getScreenTitleShort());
                    screen.setScreenType(dbBean.getScreenType());
                    screen.setId(dbBean.getId());
                }
            } catch (Exception e) {
                logger.error("Error reading Screen data, error is <%s>", e.getMessage());
                if (tx != null) {
                    tx.rollback();
                }
            }
        }
    }

    public void getRequestIntoBean(HttpServletRequest request, ScreensEntity screen) {
        /**
         * I think this should use standard BeanUtil methods (populateBean) or similar but I can't find a way to do this quickly
         * so will take a less elegant approach for now.
         */
/*
        boolean checked = request.getParameter("enabledFlag") == null ? false : request.getParameter("enabledFlag").equals("Enabled");

*/
        boolean enabledChecked = request.getParameter("enabledFlag") != null && request.getParameter("enabledFlag").equals("Enabled");
        boolean galleryChecked = request.getParameter("galleryFlag") != null && request.getParameter("galleryFlag").equals("Enabled");

        screen.setName(request.getParameter("name"));
        screen.setEnabledFlag(enabledChecked);
        screen.setGalleryFlag(galleryChecked);
        screen.setScreenContents(request.getParameter("screenContents"));
        screen.setMetaDescription(request.getParameter("metaDescription"));
        screen.setScreenTitleLong(request.getParameter("screenTitleLong"));
        screen.setScreenTitleShort(request.getParameter("screenTitleShort"));
        screen.setScreenType(request.getParameter("screenType"));
    }

    /**
     * Created to allow the left and right header images to be selected at random but will become a more
     * general purpose method for selecting images from a folder at random.
     *
     *
     * @param imagePath
     * @param numberOfImages
     *
     * @return
     */
    public List<String> selectRandomImage(String imagePath, int numberOfImages) {

        List<String> outputImages = new ArrayList<String>();
        //String webRootFullPath = this.request.getServletContext().getRealPath("/");
        //String staticFileFullPath = ((StaticFileRootURL) configuration.getConfigurationItem("staticFileRootPath")).get();
        File imagePathFile = new File(imagePath);

        // Set up to get listing of all image files within imagePath

        String[] extensions = {"jpg", "png", "jpeg"};
        FileFilter filter = new ImageFileFilter(extensions);
        File inputImages[] = imagePathFile.listFiles(filter);
        List<File> inputImageList = new ArrayList<File>(Arrays.asList(inputImages));

        /**
         * Iterate for each image file to be selected. Pick random file from those which are still in input array,
         * then remove that file from input and move to output.
         */
        Random random = new Random();
        int randomNum;
        for (int i=0; i<numberOfImages; i++) {
            randomNum = random.nextInt(inputImageList.size());
            outputImages.add(inputImageList.get(randomNum).getName());
            inputImageList.remove(randomNum);
        }
        return outputImages;
    }
}
 