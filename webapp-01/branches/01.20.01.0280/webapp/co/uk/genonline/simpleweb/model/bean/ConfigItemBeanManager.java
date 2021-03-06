package co.uk.genonline.simpleweb.model.bean;

import co.uk.genonline.simpleweb.controller.WebLogger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 17/12/2012
 * Time: 14:35
 *
 * Not sure whether this is the best way to do this.  I want to carry out some activities on a bean (such as initialising)
 * but I don't want to modify the bean declaration as this is reverse engineered from the database schema.  So I am
 * creating this utility class as a layer of management.
 *
 * Now I am doing this for the second time (first was with Screen) I should consider refactoring to make more generic.
 * ToDo Make a generic version of xxxBeanManager or find a more general way of solving the problem.
  */
public class ConfigItemBeanManager {
    WebLogger logger = new WebLogger();
    ConfigurationEntity configBean;
    SessionFactory factory;

    public ConfigItemBeanManager(SessionFactory factory) {
        this.factory = factory;
    }

    public ConfigItemBeanManager(ConfigurationEntity configBean, SessionFactory factory) {
        this.configBean = configBean;
        this.factory = factory;
    }

    public void setBean(ConfigurationEntity configBean) {
        this.configBean = configBean;
    }

    public void initialiseBean() {
        configBean.setName("");
        configBean.setValue("");
    }

    public List<Object> readConfigItems(SessionFactory factory) {
        Transaction tx = null;
        Session session;

        try {
            session = factory.getCurrentSession();
            tx = session.beginTransaction();
            String query = String.format("from ConfigurationEntity c order by name");
            logger.debug("About to execute HQL query : " + query);
            List<Object> configItems = session.createQuery(query).list();
                tx.commit();
            return configItems;
        } catch (Exception e) {
            logger.error("Error reading configuration, error is <%s>", e.getMessage());
            if (tx != null) {
                tx.rollback();
            }
            return null;
        }
    }

    public void getConfigItemIntoBean(String name) {
        Session session = factory.getCurrentSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(ConfigurationEntity.class).add(Restrictions.eq("name", name));
            ConfigurationEntity dbBean = (ConfigurationEntity) criteria.uniqueResult();
            tx.commit();
            configBean.setName(dbBean.getName());
            configBean.setValue(dbBean.getValue());
        } catch (Exception e) {
            if (tx != null) {
                tx.rollback();
            }
        }
    }

    public void getRequestIntoBean(HttpServletRequest request, ConfigurationEntity configBean) {
        /**
         * I think this should use standard BeanUtil methods (populateBean) or similar but I can't find a way to do this quickly
         * so will take a less elegant approach for now.
         */

        configBean.setName(request.getParameter("name"));
        configBean.setValue(request.getParameter("value"));
    }
}
