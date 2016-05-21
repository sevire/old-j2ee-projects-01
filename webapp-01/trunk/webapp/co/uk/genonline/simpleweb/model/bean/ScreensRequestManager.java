package co.uk.genonline.simpleweb.model.bean;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomassecondary on 11/05/2016.
 */
public class ScreensRequestManager {
    /**
     * This method is effectively a layer between the request object and the ScreensEntity JavaBean.  The reason it is
     * required is because the request object does not represent the value of a checkbox in a way which will be
     * interpreted correctly by BeanUtils.populate(), which requires "true" or "false".  This method assumes that
     * the value assigned to a checkbox is "true" if checked (this requires the HTML form to be set up correctly,
     * and will be null if unchecked.  This method overwrites the null value with "false" if it exists.
     *
     * I am still unconvinced that this is the best way of doing this but at least I have isolated where I have
     * had to write field specific code.
     *
     * @param request HttpServletRequest object passed from EE container
     * @param id      If the transaction is an update then we need to supply the id (primary key) we are updating.
     *                -1 indicates don't update id, any positive integer indicates use this id.
     */
    public static ScreensEntity moveRequestIntoScreenBean(HttpServletRequest request, int id) {

        Map requestMap = request.getParameterMap();
        Map amendedMap = new HashMap();
        amendedMap.putAll(requestMap);

        ScreenRequestDecorator requestDecorator = new ScreenRequestDecorator();
        ScreensEntity screen = new ScreensEntity();
        requestDecorator.fillBeanFromMap(screen, amendedMap);

        if (id >= 0) {
            screen.setId(id);
        }
        return screen;
    }
}
