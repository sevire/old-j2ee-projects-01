package co.uk.genonline.simpleweb.controller.actions.configactions;

import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import org.hibernate.Query;
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
public class EditConfigIndexDisplayForm extends ConfigurationAction {
    private WebLogger logger = new WebLogger();

    public EditConfigIndexDisplayForm(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    public RequestResult perform() {
        String query = String.format("from ConfigurationEntity");
        logger.debug("About to execute HQL query : " + query);
        Session session;
        Transaction tx = null;
        RequestResult requestResult = null;

         try {
             // Read database and manage transaction
             session = factory.getCurrentSession();
             tx = session.beginTransaction();
             Query queryObject = session.createQuery(query);
             java.util.List configItems = queryObject.list();
             tx.commit();

             // Process read data (won't get here if there was a technical error)
             if (configItems == null) {
                 logger.debug("configItems is null");
                 status.setStatusMessage("Error while getting configuration item list", "error");
             } else if (configItems.isEmpty()) {
                 logger.debug("configItems is empty");
                 status.setStatusMessage("No config items currently set up - click 'add' to create new item", "info");
             } else {
                 if (status.isMessageDisplayed()) {
                     status.resetStatusMessage();
                 }
             }
             request.setAttribute("configItems", configItems);
             request.setAttribute("statusType", request.getSession().getAttribute("statusType"));
             request.setAttribute("statusMessage", request.getSession().getAttribute("statusMessage"));
             requestResult = new RequestResult(request, "editConfigIndex.jsp", false);

         } catch (Exception e) {
             logger.error("Error reading configuration records.  Error is %s", e.getMessage());
             if (tx != null) {
                 tx.rollback();
             }
         }
         return requestResult;
    }
}
