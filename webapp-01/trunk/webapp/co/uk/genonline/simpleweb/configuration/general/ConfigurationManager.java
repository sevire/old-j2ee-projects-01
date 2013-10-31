package co.uk.genonline.simpleweb.configuration.general;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * @User: thomassecondary
 * @Date: 19/06/2013
 * @Time: 08:07
 *
 * Manages several sets of configuration information.
 */
public interface ConfigurationManager {

    void addConfigurationSet(String configItemName, ConfigurationItem configurationItem);

    void deleteConfigItem(String configItemName);

    Map getAllConfigItems();

    ConfigurationSet getConfigurationSet(String configurationSetName);
}
