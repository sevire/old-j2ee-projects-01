package co.uk.genonline.simpleweb.configuration.configitems;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 28/10/2013
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class ForceThumbnails extends ConfigurationItemBoolean {

    ForceThumbnails(boolean value) {
        super(value);
    }

    public ForceThumbnails(String value) {
        super(value);
    }

    public String getName() {
        return "forceThumbnails";
    }
}
