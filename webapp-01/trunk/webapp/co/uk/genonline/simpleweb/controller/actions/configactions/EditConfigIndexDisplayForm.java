package co.uk.genonline.simpleweb.controller.actions.configactions;

import co.uk.genonline.simpleweb.controller.actions.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

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

    public EditConfigIndexDisplayForm(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, SessionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        Session session = factory.openSession();
        String query = String.format("from ConfigurationEntity");
        logger.debug("About to execute HQL query : " + query);
        Query queryObject = session.createQuery(query);
        java.util.List configItems = queryObject.list();
        session.close();
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
        return new RequestResult(request, "editConfigIndex.jsp", false);
    }
}
