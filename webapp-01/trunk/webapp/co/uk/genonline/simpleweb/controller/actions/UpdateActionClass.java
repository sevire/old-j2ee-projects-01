package co.uk.genonline.simpleweb.controller.actions;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

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
abstract class UpdateActionClass extends ActionClass {
    UpdateActionClass(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, ActionData data) {
        super(request, response, factory, data);
    }

    /**
     * Common code for updating screen, called as part of add screen and update screen.  Only difference is
     * whether to add a record to the database or update existing record.
     *
     * @param addFlag
     */
    RequestResult updateScreen(boolean addFlag) {
        String descriptor = addFlag ? "add" : "update";
        String screenName = request.getParameter("name");
        logger.info(String.format(descriptor + "ing" + " screen <%s>", screenName));
        logger.debug("Screen contents from form...\n%s", request.getParameter("screenContents"));
        boolean errorFlag = false;

        if (screen.equals("")) {
            logger.info("Screen added/updated with blank name field - rejected");
            status.setStatusMessage("Name field must be populated", "error");
            errorFlag = true;
        } else {
            String sortKey = request.getParameter("sortKey").trim();
            try {
                int sortKeyInt = Integer.parseInt(sortKey);
                screen.setSortKey(sortKeyInt);
            } catch (java.lang.NumberFormatException e) {
                status.setStatusMessage("Invalid value for sort key, must be numeric", "error");
                errorFlag = true;
            }
            if (!errorFlag) {

                screenBeanManager.getRequestIntoScreenBean(request, screen);
                Session session = factory.openSession();
                logger.info(String.format("About to update data, id is <%d>", screen.getId()));
                logger.debug("Contents = \n%s", screen.getScreenContents());
                if (addFlag) {
                    session.save(screen);
                } else {
                    session.update(screen);
                }
                try {
                    session.flush();
                } catch (Exception e) {
                    status.setStatusMessage(e.getMessage(), "error");
                    logger.info("Error saving data : %s", e.getMessage());
                    errorFlag = true;
                }
            }
        }
        if (errorFlag) {
            if (addFlag) {
                logger.info("Re-displaying add screen");
                request.setAttribute("addFlag", true);
                screenBeanManager.getRequestIntoScreenBean(request, screen);
            } else {
                logger.info("Re-displaying update screen");
                request.setAttribute("addFlag", false);
                screenBeanManager.getRequestIntoScreenBean(request, screen);
            }
            return new RequestResult(request, "updateScreen.jsp", false);
        } else {
            status.setStatusMessage("Screen " + (addFlag ? "added" : "updated"), "info");
            return new RequestResult(request, "/editIndex", true);
        }
    }
}
