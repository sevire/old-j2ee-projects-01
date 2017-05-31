package co.uk.genonline.simpleweb.model.bean;

import co.uk.genonline.simpleweb.controller.WebLogger;
import org.hibernate.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by thomassecondary on 14/03/2017.
 */
public class LinksManagerNonCaching implements LinksManager {
    private WebLogger logger = new WebLogger();
    SessionFactory factory = null;
    LinksConfiguration linksConfiguration;

    /**
     *
     * @param factory Note this is injected rather than using HibernateUtils but not sure whether both are
     *                safe or whether one is better.
     *
     *                ToDo: I should be consistent about how I make Session Factory available
     */
    public LinksManagerNonCaching(SessionFactory factory, LinksConfiguration linksConfiguration) {
        this.factory = factory;
        this.linksConfiguration = linksConfiguration;
    }

    public boolean addLink(LinksEntity linkData) {
        return false;
    }

    public LinksEntityExtended getLink(String linkName) {
        return null;
    }

    public List<LinksEntityExtended> getAllLinks() {
        return getLinksFromDatabase("from LinksEntity");
    }

    public List<LinksEntityExtended> getLiveLinks() {
        return getLinksFromDatabase("from LinksEntity l where l.status = 'live' order by l.number");
        // ToDo: Add specific sort key to allow decoupling from number which is based on when the link was added
    }

    public List<LinksEntityExtended> getLiveBannerLinks() {
        return getLinksFromDatabase("from LinksEntity l where l.status = 'live' and l.bannerRequired = 1 order by l.sortKey");
        // ToDo: Add specific sort key to allow decoupling from number which is based on when the link was added
    }

    public boolean deleteLink(String linkName) {
        return false;
    }

    public boolean deleteLink(int linkNumber) {
        return false;
    }

    public LinksEntity initialiseLinksEntity() {
        LinksEntity linksEntity = new LinksEntity();
        linksEntity.setNumber(0);
        linksEntity.setName("");
        linksEntity.setLastCheckedDate(null);
        linksEntity.setStatus("not-started");
        linksEntity.setUrl("");
        linksEntity.setNextActionType("setup-backlink");
        linksEntity.setNextActionNotes("");

        return linksEntity;
    }

    private List<LinksEntityExtended> getLinksFromDatabase(String hqlQuery) {
        logger.trace("getLinksFromDatabase start : hqlQuery = <%s>", hqlQuery);
        boolean error = false;
        Session session = factory.getCurrentSession();
        List<LinksEntityExtended> links = new ArrayList<LinksEntityExtended>();
        Transaction transaction = null;
        List records;

        try {
            transaction = session.beginTransaction();

            logger.debug("getLinksFromDatabase: Have begun transaction, transaction is <%s>", transaction.toString());
            Query query = session.createQuery(hqlQuery);
            records = query.list();
            logger.debug("getLinksFromDatabase: About to commit transaction, transaction is <%s>", transaction.toString());
            transaction.commit();
            logger.debug(String.format("records read", records.size()));
        } catch (HibernateException e) {
            logger.error("Error reading Links, error is <%s>", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
            logger.error(String.format("Hibernate error reading links, error is %s", e.getMessage()));
            records = null;
        }

        if (records != null) {

            // Avoids unchecked casting warning from compiler.  Not sure how necessary this is!
            for (Object record : records) {
                if (!error) {
                    if (record instanceof LinksEntity) {
                        LinksEntityExtended link = new LinksEntityExtended((LinksEntity) record, linksConfiguration);
                        links.add(link);
                    } else {
                        error = true;
                    }
                }
            }
            if (error) {
                links = null;
            }
            logger.trace(String.format("getScreenFromDatabase end, returning <%d> screens",
                    links == null ? -1 : links.size()));
        }
        return links;
    }

}
