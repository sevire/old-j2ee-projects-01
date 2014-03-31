package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.ConfigurationEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 17/06/2013
 * Time: 08:24
 *
 * Encapsulates data objects used by the application (e.g. Screen, ConfigItems)
 */
public class ActionData {
    private final ConfigurationEntity configItems;
    private final ScreensEntity screen;

    public ActionData(ScreensEntity screen) {
        this.screen = screen;
        this.configItems = null;
    }

    public ActionData(ConfigurationEntity configItems) {
        this.screen = null;
        this.configItems = configItems;
    }

    public ActionData(ScreensEntity screen, ConfigurationEntity configItems) {
        this.screen = screen;
        this.configItems = configItems;
    }

    public ScreensEntity getScreen() {
        return screen;
    }

    public ConfigurationEntity getConfigItems() {
        return configItems;
    }
}
