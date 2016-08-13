package co.uk.genonline.simpleweb.configuration.configitems;

import co.uk.genonline.simpleweb.configuration.general.ConfigurationItem;
import org.apache.log4j.Level;


/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/06/2013
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public class ConfigurationItemLevel extends ConfigurationItem {
    Level value;

    public ConfigurationItemLevel(String value) {
        super();
        this.value = Level.toLevel(value);
        logger.debug(String.format("Instantiated value of <%s> is <%s>", getName(), getStringValue()));
    }

    public Level get() {
        return value;
    }

    public String getName() {
        return "uninitialised Level";  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getStringValue() {
        return value.toString();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String toString() {
        return getStringValue();
    }

}
