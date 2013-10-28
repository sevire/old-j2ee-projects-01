package co.uk.genonline.simpleweb.configuration;

import co.uk.genonline.simpleweb.controller.WebLogger;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/06/2013
 * Time: 08:28
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationItemInt implements ConfigurationItem {

    WebLogger logger = new WebLogger();
    String name = "uninitialised";
    int value;

    ConfigurationItemInt(int value) {
        set(value);
    }

    ConfigurationItemInt(String value) {
        int convertedValue = 0;
        logger.debug(String.format("Instantiating numGalleryColumns, string value is <%s>...", value));

        value.trim();
        try {
            convertedValue = Integer.parseInt(value);
            logger.debug(String.format("convertedValue is <%d>", convertedValue));
            set(convertedValue);
        } catch (java.lang.NumberFormatException e) {
            logger.debug(String.format("convertedValue not valid int (=<%s>), setting to <%d>", value, convertedValue));
            this.value = 0;
        }
    }

    public int get() {
        return value;
    }

    void set(int value) {
        this.value = value; // Can override for more sophisticated setting.
    }

    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getStringValue() {
        return Integer.toString(get());  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String toString() {
        return getStringValue();
    }
}
