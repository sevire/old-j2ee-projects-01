package co.uk.genonline.simpleweb.controller.screendata.displaybeans;

/**
 * This Bean holds non content specific fields which will be relevant for any screen whatever is on the page.  At the
 * time of creation there is only one field, which is:
 * - staticFileRootFullPath
 *
 * This String holds the full path of the folder (outside the webappstructure) which will store static files which
 * can be modified without delivering a new version of the webapp.
 */
public class SiteDataBean {
    String staticFileRootFullPath;

    public String getStaticFileRootFullPath() {
        return staticFileRootFullPath;
    }

    public void setStaticFileRootFullPath(String staticFileRootFullPath) {
        this.staticFileRootFullPath = staticFileRootFullPath;
    }
}