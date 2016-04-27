package co.uk.genonline.simpleweb.controller.actions.screenactions;

import co.uk.genonline.simpleweb.controller.actions.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.controller.screendata.ScreenData;
import co.uk.genonline.simpleweb.controller.screendata.ScreenDataFactory;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
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
public class ViewScreen extends ScreenAction {

    public ViewScreen(HttpServletRequest request, HttpServletResponse response, SessionFactory factory, SessionData data) {
        super(request, response, factory, data);
    }

    public RequestResult perform() {
        screen.setName(request.getParameter("screen"));
        ScreensEntity screenRecord = screenBeanManager.getScreen(screen.getName()); // openSession() Invocation #1

        String screenType;
        if (screenRecord == null) {
            response.setStatus(401);
            return new RequestResult(request, "error.jsp", false);
        } else {
            screenType = screenRecord.getScreenDisplayType();
            logger.debug(String.format("Screen = <%s>, display type = <%s>", data.getScreen().getName(), screenType));

            /**
             * The screen type determines which ScreenData object is used to collect the data together to be
             * rendered.  The ScreenData object tell us which JSP to use to render the data.
             */
            ScreenData screenData = ScreenDataFactory.getScreenData(screenType);

            /**
             * Request result will tell us whether to forward to a JSP for rendering (when just viewing data) or whether to
             * re-direct to another URL (e.g. after processing an update, re-direct to editIndex)
             */
            RequestResult result = screenData.setScreenData(request, response, screenBeanManager, data);

            /**
             * The JSP will access screenData for all the data to generate the page reply.
             */
            request.setAttribute("screenData", screenData);
            return result;
        }
    }
}
