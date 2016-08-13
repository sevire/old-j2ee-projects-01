package co.uk.genonline.simpleweb.configuration.configitems;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 28/10/2013
 * Time: 07:51
 * To change this template use File | Settings | File Templates.
 */
public class MaxThumbnailHeight extends ConfigurationItemInt {

    MaxThumbnailHeight(int value) {
        super(value);
    }

    public MaxThumbnailHeight(String value) {
        super(value);
    }

    public String getName() {
        return "maxThumbnailHeight";
    }

}
