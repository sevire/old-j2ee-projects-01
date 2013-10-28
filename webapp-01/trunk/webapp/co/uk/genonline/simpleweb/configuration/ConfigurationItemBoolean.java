package co.uk.genonline.simpleweb.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 28/10/2013
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationItemBoolean implements ConfigurationItem {
    boolean value;
    String name ="uninitialised";

    ConfigurationItemBoolean(boolean value) {
        this.value = value;
    }

    ConfigurationItemBoolean(String value) {
        this.value = Boolean.valueOf(value);
    }

    public boolean get() {
        return this.value;
    }

    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getStringValue() {
        return Boolean.toString(value);
    }
}
