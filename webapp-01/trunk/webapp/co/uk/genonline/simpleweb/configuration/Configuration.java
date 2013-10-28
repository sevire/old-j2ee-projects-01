package co.uk.genonline.simpleweb.configuration;

import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.model.bean.ConfigItemBeanManager;
import co.uk.genonline.simpleweb.model.bean.ConfigurationEntity;
import org.hibernate.SessionFactory;

import java.util.HashMap;
import java.util.Iterator;
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
    WebLogger logger = new WebLogger();
    Map<String, ConfigurationItem> configurationItems = new HashMap<String, ConfigurationItem>();
    List<Object> configurationRecords;

    public Configuration(SessionFactory factory) {
        ConfigItemBeanManager configBeanManager = new ConfigItemBeanManager(factory);

        configurationRecords = configBeanManager.readConfigItems(factory);
        logger.debug(String.format("<%d> configuration records read", configurationRecords.size()));
        ConfigurationEntity record;
        String name;
        String value;
        Iterator<Object> iterator = configurationRecords.iterator();
        while (iterator.hasNext()) {
            record = (ConfigurationEntity)iterator.next();
            name = record.getName();
            value = record.getValue();
            logger.debug(String.format("Next configuration record is: name = <%s>, value = <%s>", name, value));
            if (name.equals("homePage")) {
                addConfigItem(new HomePage(value));
            } else if (name.equals("numGalleryColumns")) {
                addConfigItem(new NumGalleryColumns(value));
            } else if (name.equals("maxThumbnailHeight")) {
                addConfigItem(new MaxThumbnailHeight(value));
            } else if (name.equals("maxThumbnailWidth")) {
                addConfigItem(new MaxThumbnailWidth(value));
            } else if (name.equals("forceGallery")) {
                addConfigItem(new ForceGallery(value));
            } else if (name.equals("forceThumbnails")) {
                addConfigItem(new ForceThumbnails(value));
            }
        }
    }

    public void addConfigItem(ConfigurationItem item) {
        configurationItems.put(item.getName(), item);
    }

    public ConfigurationItem getConfigurationItem(String name) {
        return configurationItems.get(name);
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
