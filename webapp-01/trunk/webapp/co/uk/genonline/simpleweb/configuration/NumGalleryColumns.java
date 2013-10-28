package co.uk.genonline.simpleweb.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 28/10/2013
 * Time: 07:35
 * To change this template use File | Settings | File Templates.
 */
public class NumGalleryColumns extends ConfigurationItemInt {
    final int defaultValue = 2;
    final int minValue = 1;
    final int maxValue = 10;

    NumGalleryColumns(int value) {
        super(value);
        this.name = "numGalleryColumns";
    }

    NumGalleryColumns(String value) {
        super(value);
        this.name = "numGalleryColumns";
    }

    void set(int value) {
        if (value < minValue) {
            logger.debug(String.format("convertedValue too small (=<%d>), setting to <%d>", value, minValue));
            this.value = minValue;
        } else if (value > maxValue) {
            logger.debug(String.format("convertedValue too large (=<%d>), setting to <%d>", value, maxValue));
            this.value = maxValue;
        } else {
            logger.debug(String.format("Setting to <%d>", value));
            this.value = value;
        }
    }

}
