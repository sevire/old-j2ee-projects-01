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
public class DeleteAction extends ActionClass {

    public DeleteAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        super(request, response, factory, data);
    }

    public String perform() {
        String screen = request.getParameter("screen");

        logger.info(String.format("Deleting screen <%s>", screen));

        String query = String.format("from Screens s where s.name = '%s'", screen);

        logger.info("About to execute HQL query : " + query);

        Session session = factory.openSession();
        java.util.List pages = session.createQuery(query).list();
        session.delete(pages.get(0));
        session.flush();
        return "/editIndex";
    }
}
