package co.uk.genonline.simpleweb.controller.screendata.displaybeans;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomassecondary on 20/04/16.
 *
 * Bean which stores data for the menus required on a typical web page, including those which appear in the header.
 * Will be part of the display bean which is passed as an attribute of the servlet so that the JSP can access the data
 * as a local variable.
 */
public class ScreenMenuBean {
    private Map<String, String> menuMap = new HashMap<String, String>();

    public void setMenu(String menuName, String menuHtmlString) {
        menuMap.put(menuName, menuHtmlString);
    }

    public String getMenu(String menuName) {
        return menuMap.get(menuName);
    }
}
