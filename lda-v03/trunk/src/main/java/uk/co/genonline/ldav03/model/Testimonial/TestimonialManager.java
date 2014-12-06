package uk.co.genonline.ldav03.model.Testimonial;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.genonline.ldav03.controller.UrlMapping;
import uk.co.genonline.ldav03.model.entities.TestimonialEntity;
import uk.co.genonline.ldav03.web.Html;
import uk.co.genonline.ldav03.web.LinkData;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by thomassecondary on 27/10/2014.
 */
public class TestimonialManager {
    Logger logger = Logger.getLogger("");

    @Autowired
    SessionFactory sessionFactory;

    public Testimonial getTestimonialData(String testimonialName) {
        Testimonial testimonial;
        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        TestimonialEntity entityData = (TestimonialEntity) session.get(TestimonialEntity.class, testimonialName);
        if (entityData == null) {
            testimonial = null;
        } else {
            testimonial = new Testimonial(entityData);
        }
        session.close();
        return testimonial;
    }

    /**
     * Generates a link bar for every Chamber record.  If 'this' is populated, then it represents the current page
     * and which is used within the link bar code to suppress the creation of an anchor element for that page.
     *
     * @return String which contains the HTML representing the link bar.
     */
    public String getTestimonialLinkbarHtml(String thisTestimonial) {
        logger.trace(String.format("getTestimonialLinkbarHtml: Current page is %s", thisTestimonial));
        Collection<LinkData> testimonialLinkData = getTestimonialLinkbarData();
        if (testimonialLinkData == null) {
            logger.warn(String.format("No Testimonial Records - nothing to create link bar from (shouldn't get here!)"));
            return null;
        } else {
            Html htmlObject = new Html();
            logger.trace(String.format("getTestimonialLinkbarHTML: Calling constructNavBar()"));
            return htmlObject.constructNavBar("navBar", "", testimonialLinkData, thisTestimonial);
        }
    }

    private Collection<LinkData> getTestimonialLinkbarData() {
        Session session;
        logger.log(Level.INFO, String.format("TestimonialManager:getTestimonialLinkBarHtml:sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();
        Collection<LinkData> linkData = new ArrayList<LinkData>();

        List<Object[]> testimonialEntityFieldList = (List<Object[]>) session.createQuery("select testimonialName, testimonialShortName, testimonialLongName from TestimonialEntity t").list();
        session.close();

        // Need to move data to LinkData object to ensure proper access to correct longName (in particular).

        for (Object[] entry : testimonialEntityFieldList) {
            linkData.add(new LinkData(entry[0], entry[1], entry[2], "/" + UrlMapping.TESTIMONIAL_CLASS_URL_MAPPING));
        }
        return linkData;
    }

    public void addTestimonialData(TestimonialEntity testimonialEntity) {
        logger.info("STUB: Add data for new Testimonial");
        Testimonial testimonial = new Testimonial(testimonialEntity);
        logger.info(String.format("Testimonial Data = <%s>", testimonial));

        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        session.saveOrUpdate(testimonialEntity);
        session.flush();
        session.close();
    }
}
