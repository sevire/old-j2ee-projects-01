package co.uk.genonline.simpleweb.model.configuration;

import co.uk.genonline.simpleweb.configuration.ConfigurationSet;

/**
 * Created with IntelliJ IDEA.
 *
 * @User: thomassecondary
 * @Date: 05/07/2013
 * @Time: 11:24
 *
 * Functionality to manage configuration sets within an application.
 */
public interface ConfigurationManager {

    ConfigurationSet getConfigurationSet(String configurationSetName);

    void setConfigurationSet(ConfigurationSet configurationSet);
}
