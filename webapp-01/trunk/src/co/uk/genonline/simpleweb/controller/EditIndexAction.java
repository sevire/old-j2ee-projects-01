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
        Session session = factory.openSession();
        String query = String.format("from Screens s");
        logger.info("About to execute HQL query : " + query);
        java.util.List pages = session.createQuery(query).list();
        for (Object s : pages) {
            String contents = ((Screens)s).getScreenContents();
            ((Screens)s).setScreenContents(contents.substring(0, Math.min(39, contents.length()))+"...");
        }
        request.setAttribute("editList", pages);
        return jspLocation("editIndex.jsp");
    }
}
