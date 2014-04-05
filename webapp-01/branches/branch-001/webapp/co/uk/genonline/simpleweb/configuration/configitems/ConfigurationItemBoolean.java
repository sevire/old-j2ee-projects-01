package co.uk.genonline.simpleweb.configuration.configitems;

import co.uk.genonline.simpleweb.configuration.general.ConfigurationItem;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 28/10/2013
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationItemBoolean extends ConfigurationItem {
    private final boolean value;

    ConfigurationItemBoolean(boolean value) {
        super();
        this.value = value;
        logger.debug(String.format("Instantiated value of <%s> is <%s>", getName(), getStringValue()));
    }

    ConfigurationItemBoolean(String value) {
        super();
        this.value = Boolean.valueOf(value);
        logger.debug(String.format("Instantiated value of <%s> is <%s>", getName(), getStringValue()));
    }

    public boolean get() {
        return this.value;
    }

    public String getName() {
        return "uninitialised bool";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getStringValue() {
        return Boolean.toString(value);
    }

    public String toString() {
        return getStringValue();
    }
}
