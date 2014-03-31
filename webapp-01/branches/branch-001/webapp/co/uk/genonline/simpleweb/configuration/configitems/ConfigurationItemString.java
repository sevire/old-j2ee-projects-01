package co.uk.genonline.simpleweb.configuration.configitems;

import co.uk.genonline.simpleweb.configuration.general.ConfigurationItem;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/06/2013
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationItemString extends ConfigurationItem {
    String value;

    public ConfigurationItemString(String value) {
        super();
        this.value = value; // No conversion or checking required so this is an easy one
        logger.debug(String.format("Instantiated value of <%s> is <%s>", getName(), getStringValue()));
    }

    public String get() {
        return value;
    }

    public String getName() {
        return "uninitialised string";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getStringValue() {
        return value;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String toString() {
        return getStringValue();
    }

}
