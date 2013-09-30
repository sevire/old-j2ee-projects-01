package co.uk.genonline.simpleweb.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/06/2013
 * Time: 08:41
 * To change this template use File | Settings | File Templates.
 */
public class Configuration implements ConfigurationManager {
    List<Object> configurationItems = new ArrayList<Object>();

    Configuration() {

    }


    public void addConfigItem(String configItemName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addConfigurationSet(String configItemName, ConfigurationItem configurationItem) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void deleteConfigItem(String configItemName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Map getAllConfigItems() {
        //To change body of implemented methods use File | Settings | File Templates.
        return null;
    }

    public ConfigurationSet getConfigurationSet(String configurationSetName) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
