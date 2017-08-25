package co.uk.genonline.simpleweb.controller.screendata.displaybeans;

import jdk.nashorn.api.scripting.JSObject;

/**
 * Created by thomassecondary on 14/03/2017.
 */
public class LinksDataBean {
    private String jsonString;

    public String getLinksRoot() {
        return linksRoot;
    }

    public void setLinksRoot(String linksRoot) {
        this.linksRoot = linksRoot;
    }

    private String linksRoot;

    public void setJsonString(String jsonString) {
        this.jsonString = jsonString;
    }

    public String getJsonString() {
        return jsonString;
    }


}
