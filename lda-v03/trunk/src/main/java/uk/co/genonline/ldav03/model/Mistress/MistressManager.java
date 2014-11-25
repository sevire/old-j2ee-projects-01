package uk.co.genonline.ldav03.model.Mistress;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.genonline.ldav03.controller.UrlMapping;
import uk.co.genonline.ldav03.web.LinkData;
import uk.co.genonline.ldav03.model.Testimonial.Testimonial;
import uk.co.genonline.ldav03.model.entities.MistressEntity;
import uk.co.genonline.ldav03.web.Html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Responsible for all database access related to Mistress records, and also some utility functions such as creation of
 * link bars.
 *
 * Helps to reduce number of database accesses by remembering which object is stored and only reading from database if
 * different record is required or if the record in the database has changed.
 *
 * I'm not absolutely sure whether I can do this.  As I get more complex in my database access can I guarantee that this object will
 * always know what has changed?  Let's see!
 *
 * This class should always be instantiated as a singleton (using Spring).  I have not implemented it as a Singleton class though.
 * Not sure whether I should.
 *
 * ToDo: Consider whether classes which should only have one instance should be implemented as Singleton or use Spring
 */
public class MistressManager {
    Logger logger = Logger.getLogger("");
    Mistress mistress;

    @Autowired
    SessionFactory sessionFactory;

    /**
     * Works out whether there is a valid record already stored in the object to be used for the required action.  Should
     * only read the database if not.
     *
     * @return true if can use data already stored.
     */
    private boolean isUpToDate(String mistressName) {
        return mistress == null ? false : mistressName == mistress.getMistressName();
    }

    public void clearData() {
        mistress = null;
    }

    public Mistress getMistressRecordByName(String mistressName) {
        Session session;

        if (isUpToDate(mistressName)) {
            logger.log(Level.TRACE, String.format("Returning stored Mistress record <%s>", mistress));
            return mistress;
        } else {

            logger.log(Level.TRACE, String.format("sessionFactory is <%s>", sessionFactory));
            session = sessionFactory.openSession();

            MistressEntity entityData = (MistressEntity) session.get(MistressEntity.class, mistressName);
            if (entityData == null) {
                mistress = null;
            } else {
                mistress = new Mistress(entityData);
            }
            session.close();
            return mistress;
        }
    }

    public Mistress getMistressRecordByNameWithTestimonials(String mistressName) {
        Session session;

        if (isUpToDate(mistressName)) {
            logger.log(Level.TRACE, String.format("Returning stored Mistress record <%s>", mistress));
            return mistress;
        } else {

            logger.log(Level.TRACE, String.format("sessionFactory is <%s>", sessionFactory));
            session = sessionFactory.openSession();

            MistressEntity entityData = (MistressEntity) session.get(MistressEntity.class, mistressName);
            if (entityData == null) {
                mistress = null;
            } else {
                Hibernate.initialize(entityData.getTestimonialsByMistressName());
                mistress = new Mistress(entityData);
            }
            session.close();
            return mistress;
        }
    }
    /**
     * Generates the HTML to create a link bar with all the Mistress pages.
     *
     * The query **only** retrieves the columns required so I am not storing this data centrally - I am assuming it won't be
     * re-usable.
     *
     * ToDo: Re-visit whether the approach for maintaining data is correct.
     *
     * @return
     */
    public String getMistressLinkbarHtml(String thisName) {
        Collection<LinkData> mistressLinkData = getMistressLinkbarData();
        if (mistressLinkData == null) {
            return null;
        } else {
            Html htmlObject = new Html();
            String baseUrl = UrlMapping.MISTRESS_CLASS_URL_MAPPING + UrlMapping.VIEW_URL_MAPPING;

            return htmlObject.constructNavBar(baseUrl, "navBar", "", mistressLinkData, thisName);
        }
    }

    public String getTestimonialLinkbarHtml() {
        Html htmlObject = new Html();
        String baseUrl = UrlMapping.TESTIMONIAL_CLASS_URL_MAPPING + UrlMapping.VIEW_URL_MAPPING;
        return htmlObject.constructNavBar(baseUrl, "navBar", "", getTestimonialLinkbarData(), "");
    }

    private Collection<LinkData> getMistressLinkbarData() {
        Session session;
        logger.log(Level.TRACE, String.format("MistressManager:getMistressLinkBarHtml:sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();
        Collection<LinkData> linkData = new ArrayList<LinkData>();

        List<Object[]> mistressEntityFieldList = (List<Object[]>) session.createQuery("select mistressName, mistressShortName, mistressLongName from MistressEntity m").list();
        session.close();

        // Need to move data to LinkData object to ensure proper access to correct longName (in particular).

        for (Object[] entry : mistressEntityFieldList) {
            linkData.add(new LinkData(entry[0], entry[1], entry[2], UrlMapping.MISTRESS_CLASS_URL_MAPPING));
        }
        return linkData;
    }

    private Collection<LinkData> getTestimonialLinkbarData() {
        Collection<Testimonial> testimonials = mistress.getTestimonialsByMistressName();
        Collection<LinkData> linkData = new ArrayList<LinkData>();

        for (Testimonial testimonial : testimonials) {
            linkData.add(testimonial.getLinkData());
        }
        return linkData;
    }

    public void addMistressData(MistressEntity mistressEntity) {
        logger.info("STUB: Add data for new Mistress");
        Mistress mistress = new Mistress(mistressEntity);
        logger.info(String.format("Mistress Data = <%s>", mistress));

        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        session.saveOrUpdate(mistressEntity);
        session.flush();
        session.close();

    }
}
