package uk.co.genonline.ldav03.model.chamber;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.genonline.ldav03.controller.UrlMapping;
import uk.co.genonline.ldav03.web.LinkData;
import uk.co.genonline.ldav03.model.entities.ChamberInformationEntity;
import uk.co.genonline.ldav03.web.Html;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by thomassecondary on 26/10/2014.
 */
public class ChamberInformationManager {
    Logger logger = Logger.getLogger("");

    @Autowired
    SessionFactory sessionFactory;

    public ChamberInformation getChamberInformationData(String chamberInformationName) {
        ChamberInformation chamberInformationData;
        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        ChamberInformationEntity entityData = (ChamberInformationEntity) session.get(ChamberInformationEntity.class, chamberInformationName);
        if (entityData == null) {
            chamberInformationData = null;
        } else {
            chamberInformationData = new ChamberInformation(entityData);
        }
        session.close();
        return chamberInformationData;
    }

    /**
     * Generates a link bar for every Chamber record.  If 'this' is populated, then it represents the current page
     * and which is used within the link bar code to suppress the creation of an anchor element for that page.
     *
     * @return String which contains the HTML representing the link bar.
     */
    public String getChamberLinkbarHtml(String thisChamber) {
        logger.trace(String.format("getChamberLinkbarHtml: Current page is %s", thisChamber));
        Collection<LinkData> chamberLinkData = getChamberLinkbarData();
        if (chamberLinkData == null) {
            logger.warn(String.format("No Chamber Records - nothing to create link bar from (shouldn't get here!)"));
            return null;
        } else {
            Html htmlObject = new Html();
            logger.trace(String.format("getChamberLinkbarHTML: Calling constructNavBar()"));
            return htmlObject.constructNavBar("navBar", "", chamberLinkData, thisChamber);
        }
    }

    private Collection<LinkData> getChamberLinkbarData() {
        Session session;
        logger.log(Level.INFO, String.format("ChamberManager:getChamberLinkBarHtml:sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();
        Collection<LinkData> linkData = new ArrayList<LinkData>();

        List<Object[]> chamberEntityFieldList = (List<Object[]>) session.createQuery("select chamberInformationName, chamberInformationShortName, chamberInformationLongName from ChamberInformationEntity c").list();
        session.close();

        // Need to move data to LinkData object to ensure proper access to correct longName (in particular).

        for (Object[] entry : chamberEntityFieldList) {
            linkData.add(new LinkData(entry[0], entry[1], entry[2], "/" + UrlMapping.CHAMBER_CLASS_URL_MAPPING));
        }
        return linkData;
    }

    public void addChamberInformationData(ChamberInformationEntity ChamberInformationEntity) {
        logger.info("STUB: Add data for new ChamberInformation");
        ChamberInformation ChamberInformation = new ChamberInformation(ChamberInformationEntity);
        logger.info(String.format("ChamberInformation Data = <%s>", ChamberInformation));

        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        session.saveOrUpdate(ChamberInformationEntity);
        session.flush();
        session.close();

    }

}
