package co.uk.genonline.simpleweb.configuration.general;

import co.uk.genonline.simpleweb.configuration.configitems.*;
import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.model.bean.ConfigItemBeanManager;
import co.uk.genonline.simpleweb.model.bean.ConfigurationEntity;
import org.hibernate.SessionFactory;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/06/2013
 * Time: 08:41
 * To change this template use File | Settings | File Templates.
 */
public class Configuration {
    WebLogger logger = new WebLogger();
    Map<String, ConfigurationItem> configurationItems = new HashMap<String, ConfigurationItem>();
    List<Object> configurationRecords;
    SessionFactory factory;

    public Configuration(SessionFactory factory) {
        this.factory = factory;
        loadConfiguration();
    }

    public void addConfigItem(ConfigurationItem item) {
        configurationItems.put(item.getName(), item);
    }

    public ConfigurationItem getConfigurationItem(String name) {
        return configurationItems.get(name);
    }

    public void loadConfiguration() {
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
            } else if (name.equals("maxImageWidth")) {
                addConfigItem(new MaxImageWidth(value));
            } else if (name.equals("maxImageHeight")) {
                addConfigItem(new MaxImageHeight(value));
            } else if (name.equals("forceGallery")) {
                addConfigItem(new ForceGallery(value));
            } else if (name.equals("forceThumbnails")) {
                addConfigItem(new ForceThumbnails(value));
            } else if (name.equals("blogEnabled")) {
                addConfigItem(new BlogEnabled(value));
            } else if (name.equals("galleryRoot")) {
                addConfigItem(new GalleryRoot(value));
            } else if (name.equals("loggingLevel")) {
                addConfigItem(new LoggingLevel(value));
            } else if (name.equals("thumbnailRelPath")) {
                addConfigItem(new ThumbnailRelPath(value));
            } else if (name.equals("staticFileRootURL")) {
                addConfigItem(new StaticFileRootURL(value));
            } else if (name.equals("staticFileRootFile")) {
                addConfigItem(new StaticFileRootFile(value));
            } else {
                logger.warn(String.format("Configuration item name not recognised <%s>", name));
            }
        }
        dumpAllItems();
    }

    /**
     * Just writes logging statements for each item for debugging purposes
     */
    public void dumpAllItems() {
        String itemName;
        ConfigurationItem item;
        Set<String> keys = configurationItems.keySet();
        Iterator<String> iterator = (Iterator<String>)keys.iterator();

        logger.debug("Dumping configuration items...");
        while (iterator.hasNext()) {
            itemName = iterator.next();
            item = getConfigurationItem(itemName);
            logger.debug(String.format("Configuration item <%15s> : <%s>", itemName, item.toString()));
        }
    }
}
