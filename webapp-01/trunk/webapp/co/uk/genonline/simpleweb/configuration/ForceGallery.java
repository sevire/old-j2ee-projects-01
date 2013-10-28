package co.uk.genonline.simpleweb.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 28/10/2013
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class ForceGallery extends ConfigurationItemBoolean {

    ForceGallery(boolean value) {
        super(value);
        name = "forceGallery";
    }

    ForceGallery(String value) {
        super(value);
        name = "forceGallery";
    }
}
