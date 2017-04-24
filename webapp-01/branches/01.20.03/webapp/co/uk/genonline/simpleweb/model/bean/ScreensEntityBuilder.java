package co.uk.genonline.simpleweb.model.bean;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by thomassecondary on 10/05/2016.
 */
public class ScreensEntityBuilder {
    private int id = 0;
    private int parentId;
    private String name = "";
    private String screenTitleLong = null;
    private String screenTitleShort = null;
    private String screenContents = null;
    private String metaDescription = null;
    private Boolean enabledFlag = false;
    private Boolean galleryFlag = false;
    private Timestamp created = new Timestamp(Calendar.getInstance().getTime().getTime());
    private Timestamp modified = created; // Not a bug, initialise to same as created.
    private String screenType;
    private Integer sortKey;
    private String screenDisplayType;

    public ScreensEntity build() {
        ScreensEntity screen;
        screen = new ScreensEntity();
        screen.setId(id);
        screen.setName(name);
        screen.setScreenTitleLong(screenTitleLong);
        screen.setScreenTitleShort(screenTitleShort);
        screen.setScreenContents(screenContents);
        screen.setEnabledFlag(enabledFlag);
        screen.setGalleryFlag(galleryFlag);
        screen.setMetaDescription(metaDescription);
        screen.setCreated(created);
        screen.setModified(modified);
        screen.setScreenType(screenType);
        screen.setScreenDisplayType(screenDisplayType);
        screen.setSortKey(sortKey);

        return screen;
    }

    public ScreensEntityBuilder id(int id) {
        this.id = id;
        return this;
    }

    public ScreensEntityBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ScreensEntityBuilder screenTitleLong(String screenTitleLong) {
        this.screenTitleLong = screenTitleLong;
        return this;
    }

    public ScreensEntityBuilder screenTitleShort(String screenTitleShort) {
        this.screenTitleShort = screenTitleShort;
        return this;
    }

    public ScreensEntityBuilder metaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
        return this;
    }

    public ScreensEntityBuilder screenContents(String screenContents) {
        this.screenContents = screenContents;
        return this;
    }

    public ScreensEntityBuilder enabledFlag(boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
        return this;
    }

    public ScreensEntityBuilder galleryFlag(boolean galleryFlag) {
        this.galleryFlag = galleryFlag;
        return this;
    }

    public ScreensEntityBuilder created(Timestamp created) {
        this.created = created;
        return this;
    }

    public ScreensEntityBuilder modified(Timestamp modified) {
        this.modified = modified;
        return this;
    }

    public ScreensEntityBuilder sortKey(Integer sortKey) {
        this.sortKey = sortKey;
        return this;
    }

    public ScreensEntityBuilder screenType(String screenType) {
        this.screenType = screenType;
        return this;
    }

    public ScreensEntityBuilder screenDisplayType(String screenDisplayType) {
        this.screenDisplayType = screenDisplayType;
        return this;
    }
}
