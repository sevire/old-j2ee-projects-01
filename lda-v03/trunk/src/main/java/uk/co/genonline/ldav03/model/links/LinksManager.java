package uk.co.genonline.ldav03.model.links;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.genonline.ldav03.controller.UrlMapping;
import uk.co.genonline.ldav03.model.entities.LinksEntity;
import uk.co.genonline.ldav03.web.Html;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomassecondary on 02/12/2014.
 */
public class LinksManager {
    Logger logger = Logger.getLogger("");
    Links links;

    @Autowired
    SessionFactory sessionFactory;

    @Autowired
    Html html;

    private boolean isUpToDate(String linksName) {
        return links == null ? false : linksName == links.getLinkData().getName();
    }

    public void clearData() {
        links = null;
    }

    public Links getLinkRecordByName(String linkName) {
        Session session;

        if (isUpToDate(linkName)) {
            logger.trace(String.format("Returning stored links record: [%s]", links));
            return links;
        } else {
            logger.trace(String.format("sessionFactory is <%s>", sessionFactory));

            session = sessionFactory.openSession();

            LinksEntity entityData = (LinksEntity) session.get(LinksEntity.class, linkName);
            if (entityData == null) {
                links = null;
            } else {
                links = new Links(entityData);
            }
            session.close();
            return links;
        }
    }

    public List<Links> getLinksByCategory(String linkCategory) {
        Session session;
        logger.trace(String.format("LinksManager:getLinksByCategory:sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();
        List<LinksEntity> linksEntityList = (List<LinksEntity>) session.createQuery(String.format("from LinksEntity m where linkCategory='%s' order by linkSortOrder", linkCategory)).list();
        session.close();
        if (null == linksEntityList || linksEntityList.size() == 0) {
            return null;
        } else {
            List<Links> linksList = new ArrayList<Links>();
            for (LinksEntity entity : linksEntityList) {
                linksList.add(new Links(entity));
            }
            return linksList;
        }
    }

    public String getLinksLinkbarByCategory(String linkCategory) {
        List<Links> linksList = getLinksByCategory(linkCategory);
        List<String> liContentData = new ArrayList<String>();

        for (Links linksEntry : linksList) {
            String imageRef = UrlMapping.LINK_BANNER_BASE_URL + "/" + linksEntry.getLinkBannerImageName();
            String imageElement = html.constructImgElement("", "", "/" + imageRef, "");
            String anchorElement = html.constructAnchorElement("", "", linksEntry.getLinkTargetUrl(), imageElement);
            liContentData.add(anchorElement);
        }
        String htmlString = html.constructUlElement("", "", "", true, true, true, liContentData);
        return htmlString;
    }

}
