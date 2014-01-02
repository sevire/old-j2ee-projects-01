package co.uk.genonline.simpleweb.configuration.general;

/**
 * Created with IntelliJ IDEA.
 *
 * @User: thomassecondary
 * @Date: 05/07/2013
 * @Time: 11:29
 *
 * Interface to manage a single set of configuration items
 */
public interface ConfigurationSet {
    Object getConfigurationItem(String configItemName);

    void setConfigurationItem(String configItemName, ConfigurationItem configurationItem);
}
