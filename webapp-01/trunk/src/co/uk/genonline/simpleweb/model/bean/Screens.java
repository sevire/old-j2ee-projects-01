package co.uk.genonline.simpleweb.model.bean;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 24/05/2012
 * Time: 08:09
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Screens {
    private int id;

    @javax.persistence.Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int parentId;

    @javax.persistence.Column(name = "parent_id")
    @Basic
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    private int lft;

    @javax.persistence.Column(name = "lft")
    @Basic
    public int getLft() {
        return lft;
    }

    public void setLft(int lft) {
        this.lft = lft;
    }

    private int rght;

    @javax.persistence.Column(name = "rght")
    @Basic
    public int getRght() {
        return rght;
    }

    public void setRght(int rght) {
        this.rght = rght;
    }

    private String name;

    @javax.persistence.Column(name = "name")
    @Basic
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String screenTitleLong;

    @javax.persistence.Column(name = "ScreenTitleLong")
    @Basic
    public String getScreenTitleLong() {
        return screenTitleLong;
    }

    public void setScreenTitleLong(String screenTitleLong) {
        this.screenTitleLong = screenTitleLong;
    }

    private String screenTitleShort;

    @javax.persistence.Column(name = "ScreenTitleShort")
    @Basic
    public String getScreenTitleShort() {
        return screenTitleShort;
    }

    public void setScreenTitleShort(String screenTitleShort) {
        this.screenTitleShort = screenTitleShort;
    }

    private String screenContents;

    @javax.persistence.Column(name = "ScreenContents")
    @Basic
    public String getScreenContents() {
        return screenContents;
    }

    public void setScreenContents(String screenContents) {
        this.screenContents = screenContents;
    }

    private boolean homeFlag;

    @javax.persistence.Column(name = "HomeFlag")
    @Basic
    public boolean isHomeFlag() {
        return homeFlag;
    }

    public void setHomeFlag(boolean homeFlag) {
        this.homeFlag = homeFlag;
    }

    private boolean enabledFlag;

    @javax.persistence.Column(name = "EnabledFlag")
    @Basic
    public boolean isEnabledFlag() {
        return enabledFlag;
    }

    public void setEnabledFlag(boolean enabledFlag) {
        this.enabledFlag = enabledFlag;
    }

    private Timestamp created;

    @javax.persistence.Column(name = "created")
    @Basic
    public Timestamp getCreated() {
        return created;
    }

    public void setCreated(Timestamp created) {
        this.created = created;
    }

    private Timestamp modified;

    @javax.persistence.Column(name = "modified")
    @Basic
    public Timestamp getModified() {
        return modified;
    }

    public void setModified(Timestamp modified) {
        this.modified = modified;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Screens screens = (Screens) o;

        if (enabledFlag != screens.enabledFlag) return false;
        if (homeFlag != screens.homeFlag) return false;
        if (id != screens.id) return false;
        if (lft != screens.lft) return false;
        if (parentId != screens.parentId) return false;
        if (rght != screens.rght) return false;
        if (created != null ? !created.equals(screens.created) : screens.created != null) return false;
        if (modified != null ? !modified.equals(screens.modified) : screens.modified != null) return false;
        if (name != null ? !name.equals(screens.name) : screens.name != null) return false;
        if (screenContents != null ? !screenContents.equals(screens.screenContents) : screens.screenContents != null)
            return false;
        if (screenTitleLong != null ? !screenTitleLong.equals(screens.screenTitleLong) : screens.screenTitleLong != null)
            return false;
        if (screenTitleShort != null ? !screenTitleShort.equals(screens.screenTitleShort) : screens.screenTitleShort != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + parentId;
        result = 31 * result + lft;
        result = 31 * result + rght;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (screenTitleLong != null ? screenTitleLong.hashCode() : 0);
        result = 31 * result + (screenTitleShort != null ? screenTitleShort.hashCode() : 0);
        result = 31 * result + (screenContents != null ? screenContents.hashCode() : 0);
        result = 31 * result + (homeFlag ? 1 : 0);
        result = 31 * result + (enabledFlag ? 1 : 0);
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (modified != null ? modified.hashCode() : 0);
        return result;
    }
}
