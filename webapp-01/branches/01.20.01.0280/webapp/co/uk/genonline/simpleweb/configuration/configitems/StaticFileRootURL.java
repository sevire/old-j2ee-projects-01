package co.uk.genonline.simpleweb.configuration.configitems;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 28/10/2013
 * Time: 07:27
 * To change this template use File | Settings | File Templates.
 */
public class StaticFileRootURL extends ConfigurationItemString {

    public StaticFileRootURL(String value) {
        super(value);
    }

    public String getName() {
        return "staticFileRootURL";
    }
}
