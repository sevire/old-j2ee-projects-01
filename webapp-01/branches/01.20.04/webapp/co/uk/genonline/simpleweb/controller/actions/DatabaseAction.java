package co.uk.genonline.simpleweb.controller.actions;

import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 26/10/2013
 * Time: 15:43
 * To change this template use File | Settings | File Templates.
 */
public abstract class DatabaseAction extends Action {
    final protected SessionFactory factory;

    protected DatabaseAction(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        ServletContext context = request.getServletContext();
        this.factory = (SessionFactory)context.getAttribute("sessionFactory");
        logger.info(String.format("context = <%s>, sessionFactory from context = <%s>", context, factory));
    }

    public String toString() {
        return String.format("%s, Factory: <%s>", super.toString(), factory);
    }
}
