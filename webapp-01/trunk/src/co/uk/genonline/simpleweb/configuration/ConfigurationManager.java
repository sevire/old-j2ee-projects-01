package co.uk.genonline.simpleweb.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/06/2013
 * Time: 08:07
 * To change this template use File | Settings | File Templates.
 */
public interface ConfigurationManager {

    void addConfigItem(String configItemName);

    void deleteConfigItem(String configItemName);

    void getAllConfigItems();

    String toString();

}
