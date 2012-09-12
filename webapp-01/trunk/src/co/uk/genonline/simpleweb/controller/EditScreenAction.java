package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.model.bean.Screens;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:09
 * To change this template use File | Settings | File Templates.
 */
public class EditScreenAction extends ActionClass {

    public EditScreenAction(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, Screens data) {
        super(request, response, factory, data);
    }

    public String perform() {
        Session session = factory.openSession();
        Criteria criteria = session.createCriteria(Screens.class).add(Restrictions.eq("name", data.getName()));
        //data = (Screens) criteria.uniqueResult();
        BeanUtils.copyProperties(criteria.uniqueResult(), data);
        return jspLocation("editScreen.jsp");
    }
}
