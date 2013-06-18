package co.uk.genonline.simpleweb.model.bean;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.servlet.http.HttpServletRequest;

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
    ConfigItems configBean;
    SessionFactory factory;

    public ConfigItemBeanManager(ConfigItems configBean, SessionFactory factory) {
        this.configBean = configBean;
        this.factory = factory;
    }

    public void initialiseBean() {
        configBean.setName("");
        configBean.setValue("");
    }

    public void getConfigItemIntoBean(String name) {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(ConfigItems.class).add(Restrictions.eq("name", name));
        ConfigItems dbBean = (ConfigItems) criteria.uniqueResult();

        configBean.setName(dbBean.getName());
        configBean.setValue(dbBean.getValue());
    }

    public void getRequestIntoBean(HttpServletRequest request) {
        /**
         * I think this should use standard BeanUtil methods (populateBean) or similar but I can't find a way to do this quickly
         * so will take a less elegant approach for now.
         */

        configBean.setName(request.getParameter("name"));
        configBean.setValue(request.getParameter("value"));
    }

}
