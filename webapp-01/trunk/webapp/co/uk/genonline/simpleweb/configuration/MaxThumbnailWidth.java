package co.uk.genonline.simpleweb.configuration;

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
        name = "maxThumbnailWidth";
    }

    MaxThumbnailWidth(String value) {
        super(value);
        name = "maxThumbnailWidth";
    }
}
