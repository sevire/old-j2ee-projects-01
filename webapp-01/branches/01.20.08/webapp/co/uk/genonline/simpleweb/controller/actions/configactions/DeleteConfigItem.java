package co.uk.genonline.simpleweb.controller.actions.configactions;

import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:09
 * To change this template use File | Settings | File Templates.
 */
public class DeleteConfigItem extends ConfigurationAction {

    public DeleteConfigItem(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public RequestResult perform() {
        String name = request.getParameter("name");

        logger.info(String.format("Deleting config item <%s>", configItems));

        String query = String.format("from ConfigurationEntity c where c.name = '%s'", name);

        logger.debug("About to execute HQL query to delete config item : " + query);

        RequestResult requestResult = null;
        Session session;
        Transaction transaction = null;
        try {
            session = factory.getCurrentSession();
            transaction = session.beginTransaction();
            java.util.List configItems = session.createQuery(query).list();
            session.delete(configItems.get(0));
            transaction.commit();
            requestResult = new RequestResult(request, "/editConfigIndex", true);
        } catch (Exception e) {
            logger.error("Error deleting a configuration item, message is <%s>", e.getMessage());
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return requestResult;
    }
}
