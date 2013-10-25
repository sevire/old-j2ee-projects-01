package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.model.bean.ConfigItems;
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
    private final ConfigItems configItems;
    private final ScreensEntity screen;

    public ActionData(ScreensEntity screen) {
        this.screen = screen;
        this.configItems = null;
    }

    public ActionData(ConfigItems configItems) {
        this.screen = null;
        this.configItems = configItems;
    }

    public ActionData(ScreensEntity screen, ConfigItems configItems) {
        this.screen = screen;
        this.configItems = configItems;
    }

    public ScreensEntity getScreen() {
        return screen;
    }

    public ConfigItems getConfigItems() {
        return configItems;
    }
}
