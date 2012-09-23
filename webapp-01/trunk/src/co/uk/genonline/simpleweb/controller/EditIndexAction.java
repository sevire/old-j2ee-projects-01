package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.model.bean.Screens;
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
public class EditIndexAction extends ActionClass {

    public EditIndexAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        super(request, response, factory, data);
    }

    public String perform() {
        String statusType;
        String statusMessage;
        Session session = factory.openSession();
        String query = String.format("from Screens s order by screenType");
        logger.info("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        if (pages == null) {
            statusType = "error";
            statusMessage = "Error while getting page list";
        } else if (pages.isEmpty()) {
            statusType = "warning";
            statusMessage = "No pages currently set up - click 'add' to create new page";
        } else {
            statusType = "info";
            statusMessage = "OK";
        }
        for (Object s : pages) {
            String contents = ((Screens)s).getScreenContents();
            ((Screens)s).setScreenContents(contents.substring(0, Math.min(39, contents.length()))+"...");
        }
        request.setAttribute("editList", pages);
        request.setAttribute("statusType", request.getSession().getAttribute("statusType"));
        request.setAttribute("statusMessage", request.getSession().getAttribute("statusMessage"));
        return jspLocation("editIndex.jsp");
    }
}
