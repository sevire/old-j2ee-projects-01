package co.uk.genonline.simpleweb.configuration.configitems;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 28/10/2013
 * Time: 07:51
 * To change this template use File | Settings | File Templates.
 */
public class MaxThumbnailWidth extends ConfigurationItemInt {

    MaxThumbnailWidth(int value) {
        super(value);
    }

    public MaxThumbnailWidth(String value) {
        super(value);
    }

    public String getName() {
        return "maxThumbnailWidth";
    }


}
