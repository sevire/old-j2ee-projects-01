package co.uk.genonline.simpleweb.configuration.configitems;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 28/10/2013
 * Time: 07:35
 * To change this template use File | Settings | File Templates.
 */
public class NumGalleryColumns extends ConfigurationItemInt {

    NumGalleryColumns(int value) {
        super(value);
    }

    public NumGalleryColumns(String value) {
        super(value);
    }

    public String getName() {
        return "numGalleryColumns";
    }
}
