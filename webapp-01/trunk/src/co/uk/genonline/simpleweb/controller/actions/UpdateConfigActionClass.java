package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.ConfigItemBeanManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 10/12/2012
 * Time: 22:20
 *
 * This is an extension to ActionClass which includes a common method to be used by ActionClasses which
 * update a screen.  Created in order to replace duplicate code fragment between add screen and update
 * screen.
 */
public abstract class UpdateConfigActionClass extends ActionClass {
    public UpdateConfigActionClass(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super(request, response, factory, data);
    }

    /**
     * Common code for updating screen, called as part of add screen and update screen.  Only difference is
     * whether to add a record to the database or update existing record.
     *
     * @param addFlag
     */
    RequestResult updateConfigItem(boolean addFlag) {
        String descriptor = addFlag ? "add" : "update";
        String name = request.getParameter("name");
        logger.info(String.format(descriptor + "ing" + " config item <%s>", name));
        boolean errorFlag = false;
        ConfigItemBeanManager configItemBeanManager = new ConfigItemBeanManager(configItems, factory);

        if (name.equals("")) {
            logger.info("Config item added/updated with blank name field - rejected");
            status.setStatusMessage("Name field must be populated", "error");
            errorFlag = true;
        } else {
            configItemBeanManager.getRequestIntoBean(request);
            Session session = factory.openSession();
            logger.info(String.format("About to update config item data, name is <%s>, value is <%s>", name, this.configItems.getValue()));
            Transaction transaction = session.beginTransaction();
            if (addFlag) {
                session.save(this.configItems);
            } else {
                session.update(this.configItems);
            }
            try {
                logger.info("About to flush session");
                transaction.commit();
            } catch (Exception e) {
                status.setStatusMessage(e.getMessage(), "error");
                logger.info("Error saving config data : %s", e.getMessage());
                transaction.rollback();
                errorFlag = true;
            }
        }
        if (errorFlag) {
            if (addFlag) {
                logger.info("Re-displaying add config item screen");
                request.setAttribute("addFlag", true);
                configItemBeanManager.getRequestIntoBean(request);
            } else {
                logger.info("Re-displaying update config item screen");
                request.setAttribute("addFlag", false);
                configItemBeanManager.getRequestIntoBean(request);
                //webHelper.getScreenIntoBean(data, data.getName());
            }
            return new RequestResult(jspLocation("updateConfigItem.jsp"), false);
        } else {
            status.setStatusMessage("Config item " + (addFlag ? "added" : "updated"), "info");
            return new RequestResult(URLwithContext("/editConfigIndex"), true);
        }
    }
}
