package uk.co.genonline.ldav03.model.Mistress;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.genonline.ldav03.controller.UrlMapping;
import uk.co.genonline.ldav03.web.Html;

import java.util.List;

/**
 *
 *
 */
public class MistressManager {
    Logger logger = Logger.getLogger("");

    @Autowired
    SessionFactory sessionFactory;

    public Mistress getMistressData(String mistressName) {
        Mistress mistressData;
        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        MistressEntity entityData = (MistressEntity) session.get(MistressEntity.class, mistressName);
        if (entityData == null) {
            mistressData = null;
        } else {
            mistressData = new Mistress(entityData);
        }
        session.close();
        return mistressData;
    }

    /**
     * Generates the HTML to create a link bar with all the Mistress pages.
     *
     * @return
     */
    public String getMistressLinkbarHtml() {
        Session session;
        logger.log(Level.INFO, String.format("MistressManager:getMistressLinkBarHtml:sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        List<Object[]> mistressEntityFieldList = (List<Object[]>) session.createQuery("select mistressName, mistressShortName from MistressEntity m").list();
        session.close();
        if (mistressEntityFieldList == null) {
            return null;
        } else {
            // Wrap each entity with a Mistress object and add to list
            Html htmlObject = new Html();
            String baseUrl = UrlMapping.MISTRESS_CLASS_URL_MAPPING + UrlMapping.MISTRESS_VIEW_URL_MAPPING;

            return htmlObject.constructNavBar(baseUrl, "navBar", "", mistressEntityFieldList);
        }
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
