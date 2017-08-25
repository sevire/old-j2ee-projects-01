package co.uk.genonline.simpleweb.model.bean;

import co.uk.genonline.simpleweb.controller.WebLogger;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Responsible for transforming parameters that are received from a web form for a new Screen into a Screens object
 * which can then be used to update the database.
 */
public class ScreenRequestDecorator {
    WebLogger logger = new WebLogger();

    /**
     *
     * @param requestMap Map of request parameters (normally extracted from HttpRequest object)
     * @param setCreatedFlag Set if we need to set the createdDate (for new records)
     * @return ScreensEntity object with fields populated from request but translating between Http format and
     * appropriate Java format for bean as required.
     *
     * Also sets
     *
     * The following fields need transforming:
     * - enabledFlag
     * - galleryFlag
     *
     * ToDo Check that this is working as expected and clarify what I am trying to do here!
     */
    public ScreensEntity getScreen(Map requestMap, boolean setCreatedFlag) {
        Map amendedMap = new HashMap();
        ScreensEntity screen = new ScreensEntity();

        amendedMap.putAll(requestMap);
        if (amendedMap.get("enabledFlag") == null) {
            logger.info("enabledFlag from request is <null>");
            amendedMap.put("enabledFlag", "false");
        } else {
            logger.info("enabledFlag from request is <%s>", amendedMap.get("enabledFlag").toString());
        }
        if (amendedMap.get("galleryFlag") == null) {
            logger.info("galleryFlag from request is <null>");
            amendedMap.put("galleryFlag", "false");
        } else {
            logger.info("galleryFlag from request is <%s>", amendedMap.get("galleryFlag").toString());
        }

        fillBeanFromMap(screen, amendedMap);

        // ToDo: Work out right way to solve Timestamp crashing problem
        java.sql.Timestamp timeStamp = new java.sql.Timestamp(Calendar.getInstance().getTime().getTime());

        // Always set modified date / time.  Only set created date / time if flag set (wouldn't do for an update)

        screen.setModified(timeStamp);
        if (setCreatedFlag) {
            screen.setCreated(timeStamp);
        }

        return screen;
    }

    /**
     * Uses BeanUtils (populate) to transfer data from a Map to the appropriate bean.
     *
     * In order to work the values in the map must have names which are the same as the field names in the bean.
     * The method has been designed to work with request.getParameterMap() in mind.
     *
     * Please note that if using request.getParameterMap() to generate the Map to pass into this method, that
     * checkbox parameters in a request will need to be pre-processed so that the value is 'true' or 'false'.
     *
     * @param data
     * @param parameterMap
     */
    public void fillBeanFromMap(Object data, Map parameterMap) {
        try {
            BeanUtils.populate(data, parameterMap);
        } catch (IllegalAccessException e) {
            logger.error("Populate - Illegal Access", e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            logger.error("Populate - Invocation Target", e.getMessage());
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

}
