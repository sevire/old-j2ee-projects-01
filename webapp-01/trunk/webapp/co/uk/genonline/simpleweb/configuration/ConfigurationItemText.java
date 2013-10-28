package co.uk.genonline.simpleweb.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/06/2013
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationItemText implements ConfigurationItem {
    String name =  "uninitialised";
    String value;

    public ConfigurationItemText(String value) {
        this.value = value; // No conversion or checking required so this is an easy one
    }

    public String get() {
        return value;
    }

    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getStringValue() {
        return value;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String toString() {
        return getStringValue();
    }

}
