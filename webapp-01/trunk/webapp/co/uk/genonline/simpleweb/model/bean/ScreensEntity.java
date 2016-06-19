package co.uk.genonline.simpleweb.model.bean;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 24/10/2013
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Screens", schema = "") // ToDo Suppress inclusion of catalog when generating entity class (hard codes db name)
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

    @Column(name = "id")
    @GeneratedValue
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "parent_id")
    @Basic
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "ScreenTitleLong")
    @Basic
    public String getScreenTitleLong() {
        return screenTitleLong;
    }

    public void setScreenTitleLong(String screenTitleLong) {
        this.screenTitleLong = screenTitleLong;
    }

    @Column(name = "ScreenTitleShort")
    @Basic
    public String getScreenTitleShort() {
        return screenTitleShort;
    }

    public void setScreenTitleShort(String screenTitleShort) {
        this.screenTitleShort = screenTitleShort;
    }

    @Column(name = "ScreenContents")
    @Basic
    public String getScreenContents() {
        return screenContents;
    }

    public void setScreenContents(String screenContents) {
        this.screenContents = screenContents;
    }

    @Column(name = "MetaDescription")
    @Basic
    public String getMetaDescription() {
        return metaDescription;
    }

    public void setMetaDescription(String metaDescription) {
        this.metaDescription = metaDescription;
    }

    @Column(name = "EnabledFlag")
    @Basic
    public Boolean getEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(Boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    @Column(name = "GalleryFlag")
    @Basic
    public Boolean getGalleryFlag() {
        return galleryFlag;
    }

    public void setGalleryFlag(Boolean galleryFlag) {
        this.galleryFlag = galleryFlag;
    }

    @Column(name = "created")
    @Basic
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    @Column(name = "modified")
    @Basic
    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    @Column(name = "ScreenType")
    @Basic
    public String getScreenType() {
        return screenType;
    }

    public void setScreenType(String screenType) {
        this.screenType = screenType;
    }

    @Column(name = "SortKey")
    @Basic
    public Integer getSortKey() {
        return sortKey;
    }

    public void setSortKey(Integer sortKey) {
        this.sortKey = sortKey;
    }

    @Column(name = "ScreenDisplayType")
    @Basic
    public String getScreenDisplayType() {
        return screenDisplayType;
    }

    public void setScreenDisplayType(String screenDisplayType) {
        this.screenDisplayType = screenDisplayType;
    }

    /**
     * Modified by TG.
     *
     * Removed test for modified and created date as I want the objects to be the same if the data is
     * the same regardless of when they were created or last updated.
     *
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScreensEntity that = (ScreensEntity) o;

        if (id != that.id) return false;
        if (parentId != that.parentId) return false;
        // **TG** if (created != null ? !created.equals(that.created) : that.created != null) return false;
        if (enabledFlag != null ? !enabledFlag.equals(that.enabledFlag) : that.enabledFlag != null) return false;
        if (galleryFlag != null ? !galleryFlag.equals(that.galleryFlag) : that.galleryFlag != null) return false;
        if (metaDescription != null ? !metaDescription.equals(that.metaDescription) : that.metaDescription != null) return false;
        // **TG** if (modified != null ? !modified.equals(that.modified) : that.modified != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (screenContents != null ? !screenContents.equals(that.screenContents) : that.screenContents != null) return false;
        if (screenDisplayType != null ? !screenDisplayType.equals(that.screenDisplayType) : that.screenDisplayType != null) return false;
        if (screenTitleLong != null ? !screenTitleLong.equals(that.screenTitleLong) : that.screenTitleLong != null) return false;
        if (screenTitleShort != null ? !screenTitleShort.equals(that.screenTitleShort) : that.screenTitleShort != null) return false;
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

/*
    public String toString() {
        int contentLength = getScreenContents().length();
        String included = contentLength > 10 ? getScreenContents().substring(0,9) : getScreenContents();

        return String.format("ScreensEntity::id:%d, name:%s, short:%s, long:%s, text:<%s>",
                getId(),
                getName(),
                getScreenTitleShort(),
                getScreenTitleLong(),
                included);
    }
*/
}
