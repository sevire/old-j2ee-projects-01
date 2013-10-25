package co.uk.genonline.simpleweb.model.bean;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 24/10/2013
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
@javax.persistence.Table(name = "Screens", schema = "", catalog = "lda_dev")
@Entity
public class ScreensEntity {
    private int id;
    private int parentId;
    private String name;
    private String screenTitleLong;
    private String screenTitleShort;
    private String screenContents;
    private String metaDescription;
    private Boolean enabledFlag;
    private Boolean galleryFlag;
    private Timestamp created;
    private Timestamp modified;
    private String screenType;
    private Integer sortKey;
    private String screenDisplayType;

    @javax.persistence.Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @javax.persistence.Column(name = "parent_id")
    @Basic
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @javax.persistence.Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @javax.persistence.Column(name = "ScreenTitleLong")
    @Basic
    public String getScreenTitleLong() {
        return screenTitleLong;
    }

    public void setScreenTitleLong(String screenTitleLong) {
        this.screenTitleLong = screenTitleLong;
    }

    @javax.persistence.Column(name = "ScreenTitleShort")
    @Basic
    public String getScreenTitleShort() {
        return screenTitleShort;
    }

    public void setScreenTitleShort(String screenTitleShort) {
        this.screenTitleShort = screenTitleShort;
    }

    @javax.persistence.Column(name = "ScreenContents")
    @Basic
    public String getScreenContents() {
        return screenContents;
    }

    public void setScreenContents(String screenContents) {
        this.screenContents = screenContents;
    }

    @javax.persistence.Column(name = "MetaDescription")
    @Basic
    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    @javax.persistence.Column(name = "EnabledFlag")
    @Basic
    public Boolean getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    @javax.persistence.Column(name = "GalleryFlag")
    @Basic
    public Boolean getGalleryFlag() {
        return galleryFlag;
    }

    public void setGalleryFlag(Boolean galleryFlag) {
        this.galleryFlag = galleryFlag;
    }

    @javax.persistence.Column(name = "created")
    @Basic
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @javax.persistence.Column(name = "modified")
    @Basic
    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    @javax.persistence.Column(name = "ScreenType")
    @Basic
    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    @javax.persistence.Column(name = "SortKey")
    @Basic
    public Integer getSortKey() {
        return sortKey;
    }

    public void setSortKey(Integer sortKey) {
        this.sortKey = sortKey;
    }

    @javax.persistence.Column(name = "ScreenDisplayType")
    @Basic
    public String getScreenDisplayType() {
        return screenDisplayType;
    }

    public void setScreenDisplayType(String screenDisplayType) {
        this.screenDisplayType = screenDisplayType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScreensEntity that = (ScreensEntity) o;

        if (id != that.id) return false;
        if (parentId != that.parentId) return false;
        if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (enabledFlag != null ? !enabledFlag.equals(that.enabledFlag) : that.enabledFlag != null) return false;
        if (galleryFlag != null ? !galleryFlag.equals(that.galleryFlag) : that.galleryFlag != null) return false;
        if (metaDescription != null ? !metaDescription.equals(that.metaDescription) : that.metaDescription != null)
            return false;
        if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (screenContents != null ? !screenContents.equals(that.screenContents) : that.screenContents != null)
            return false;
        if (screenDisplayType != null ? !screenDisplayType.equals(that.screenDisplayType) : that.screenDisplayType != null)
            return false;
        if (screenTitleLong != null ? !screenTitleLong.equals(that.screenTitleLong) : that.screenTitleLong != null)
            return false;
        if (screenTitleShort != null ? !screenTitleShort.equals(that.screenTitleShort) : that.screenTitleShort != null)
            return false;
        if (screenType != null ? !screenType.equals(that.screenType) : that.screenType != null) return false;
        if (sortKey != null ? !sortKey.equals(that.sortKey) : that.sortKey != null) return false;

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
        result = 31 * result + (metaDescription != null ? metaDescription.hashCode() : 0);
        result = 31 * result + (enabledFlag != null ? enabledFlag.hashCode() : 0);
        result = 31 * result + (galleryFlag != null ? galleryFlag.hashCode() : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        result = 31 * result + (screenType != null ? screenType.hashCode() : 0);
        result = 31 * result + (sortKey != null ? sortKey.hashCode() : 0);
        result = 31 * result + (screenDisplayType != null ? screenDisplayType.hashCode() : 0);
        return result;
    }
}
