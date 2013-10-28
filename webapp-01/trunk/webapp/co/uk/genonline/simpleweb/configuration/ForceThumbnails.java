package co.uk.genonline.simpleweb.configuration;

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
        name = "forceThumbnails";
    }

    ForceThumbnails(String value) {
        super(value);
        name = "forceThumbnails";
    }
}
