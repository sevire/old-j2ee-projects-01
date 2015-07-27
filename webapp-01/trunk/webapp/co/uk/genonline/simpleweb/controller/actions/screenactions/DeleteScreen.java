package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.ActionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
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
public class DeleteScreen extends ScreenAction {

    public DeleteScreen(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {

        logger.info(String.format("Deleting screen <%s>", screen));

        screen.setName(request.getParameter("screen"));
        screenBeanManager.getScreen(screen);

        Session session = factory.openSession();
        session.delete(screen);
        session.flush();
        session.close();
        return new RequestResult(request, "/editIndex", true);
    }
}
