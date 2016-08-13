package co.uk.genonline.simpleweb.configuration.configitems;

import co.uk.genonline.simpleweb.configuration.general.ConfigurationItem;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/06/2013
 * Time: 08:28
 * To change this template use File | Settings | File Templates.
 */
public abstract class ConfigurationItemInt extends ConfigurationItem {

    int value;

    ConfigurationItemInt(int value) {
        super();
        set(value);
        logger.debug(String.format("Instantiated value of <%s> is <%s>", getName(), getStringValue()));
    }

    ConfigurationItemInt(String value) {
        super();
        int convertedValue = 0;
        logger.debug(String.format("Instantiating <%s>, string value is <%s>...", getName(), value));

        value.trim();
        try {
            convertedValue = Integer.parseInt(value);
            logger.debug(String.format("convertedValue is <%d>", convertedValue));
            set(convertedValue);
        } catch (java.lang.NumberFormatException e) {
            logger.debug(String.format("convertedValue not valid int (=<%s>), setting to <%d>", value, convertedValue));
            this.value = 0;
        }
        logger.debug(String.format("Instantiated value of <%s> is <%s>", getName(), getStringValue()));
    }

    public int get() {
        return value;
    }

    void set(int value) {
        this.value = value; // Can override for more sophisticated setting.
    }

    public String getName() {
        return "uninitialised int";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getStringValue() {
        return Integer.toString(get());  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String toString() {
        return getStringValue();
    }
}
