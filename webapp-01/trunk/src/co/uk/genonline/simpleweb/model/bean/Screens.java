package co.uk.genonline.simpleweb.model.bean;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 11/06/2012
 * Time: 17:26
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Screens {
    private int id;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int parentId;

    @Column(name = "parent_id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    private String name;

    @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String screenTitleLong;

    @Column(name = "ScreenTitleLong", nullable = true, insertable = true, updatable = true, length = 50, precision = 0)
    @Basic
    public String getScreenTitleLong() {
        return screenTitleLong;
    }

    public void setScreenTitleLong(String screenTitleLong) {
        this.screenTitleLong = screenTitleLong;
    }

    private String screenTitleShort;

    @Column(name = "ScreenTitleShort", nullable = true, insertable = true, updatable = true, length = 20, precision = 0)
    @Basic
    public String getScreenTitleShort() {
        return screenTitleShort;
    }

    public void setScreenTitleShort(String screenTitleShort) {
        this.screenTitleShort = screenTitleShort;
    }

    private String screenContents;

    @Column(name = "ScreenContents", nullable = true, insertable = true, updatable = true, length = 65535, precision = 0)
    @Basic
    public String getScreenContents() {
        return screenContents;
    }

    public void setScreenContents(String screenContents) {
        this.screenContents = screenContents;
    }

    private boolean enabledFlag;

    @Column(name = "EnabledFlag", nullable = true, insertable = true, updatable = true, length = 0, precision = 0)
    @Basic
    public boolean isEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    private Timestamp created;

    @Column(name = "created", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    private Timestamp modified;

    @Column(name = "modified", nullable = true, insertable = true, updatable = true, length = 19, precision = 0)
    @Basic
    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    private String screenType;

    @Column(name = "ScreenType", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
    @Basic
    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screens screens = (Screens) o;

        if (enabledFlag != screens.enabledFlag) return false;
        if (id != screens.id) return false;
        if (parentId != screens.parentId) return false;
        if (created != null ? !created.equals(screens.created) : screens.created != null) return false;
        if (modified != null ? !modified.equals(screens.modified) : screens.modified != null) return false;
        if (name != null ? !name.equals(screens.name) : screens.name != null) return false;
        if (screenContents != null ? !screenContents.equals(screens.screenContents) : screens.screenContents != null)
            return false;
        if (screenTitleLong != null ? !screenTitleLong.equals(screens.screenTitleLong) : screens.screenTitleLong != null)
            return false;
        if (screenTitleShort != null ? !screenTitleShort.equals(screens.screenTitleShort) : screens.screenTitleShort != null)
            return false;
        if (screenType != null ? !screenType.equals(screens.screenType) : screens.screenType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + parentId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (screenTitleLong != null ? screenTitleLong.hashCode() : 0);
        result = 31 * result + (screenTitleShort != null ? screenTitleShort.hashCode() : 0);
        result = 31 * result + (screenContents != null ? screenContents.hashCode() : 0);
        result = 31 * result + (enabledFlag ? 1 : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + (screenType != null ? screenType.hashCode() : 0);
        return result;
    }

    private boolean galleryFlag;

    @Column(name = "GalleryFlag", nullable = true, insertable = true, updatable = true, length = 0, precision = 0)
    @Basic
    public boolean isGalleryFlag() {
        return galleryFlag;
    }

    public void setGalleryFlag(boolean galleryFlag) {
        this.galleryFlag = galleryFlag;
    }

    private int sortKey;

    @Column(name = "SortKey", nullable = true, insertable = true, updatable = true, length = 10, precision = 0)
    @Basic
    public int getSortKey() {
        return sortKey;
    }

    public void setSortKey(int sortKey) {
        this.sortKey = sortKey;
    }
}
