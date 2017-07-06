package co.uk.genonline.simpleweb.model.bean;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by thomassecondary on 14/03/2017.
 */
@Entity
@Table(name = "Links")
public class LinksEntity {
    protected int number;
    protected String name;
    protected String bannerImageName;
    protected String status;
    protected String url;
    protected Date lastCheckedDate;
    protected String nextActionType;
    protected String nextActionNotes;
    protected Integer sortKey;
    protected String userName;
    protected String password;
    protected byte bannerRequired;

    @Id
    @Column(name = "number", nullable = false)
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Basic
    @Column(name = "name", nullable = false, length = 100)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "bannerImageName", nullable = true, length = 45)
    public String getBannerImageName() {
        return bannerImageName;
    }

    public void setBannerImageName(String bannerImageName) {
        this.bannerImageName = bannerImageName;
    }

    @Basic
    @Column(name = "status", nullable = true, length = 45)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Basic
    @Column(name = "url", nullable = true, length = 100)
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Basic
    @Column(name = "lastCheckedDate", nullable = true)
    public Date getLastCheckedDate() {
        return lastCheckedDate;
    }

    public void setLastCheckedDate(Date lastCheckedDate) {
        this.lastCheckedDate = lastCheckedDate;
    }

    @Basic
    @Column(name = "nextActionType", nullable = true, length = 45)
    public String getNextActionType() {
        return nextActionType;
    }

    public void setNextActionType(String nextActionType) {
        this.nextActionType = nextActionType;
    }

    @Basic
    @Column(name = "nextActionNotes", nullable = true, length = -1)
    public String getNextActionNotes() {
        return nextActionNotes;
    }

    public void setNextActionNotes(String nextActionNotes) {
        this.nextActionNotes = nextActionNotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinksEntity that = (LinksEntity) o;

        if (number != that.number) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (bannerImageName != null ? !bannerImageName.equals(that.bannerImageName) : that.bannerImageName != null)
            return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        if (lastCheckedDate != null ? !lastCheckedDate.equals(that.lastCheckedDate) : that.lastCheckedDate != null)
            return false;
        if (nextActionType != null ? !nextActionType.equals(that.nextActionType) : that.nextActionType != null)
            return false;
        if (nextActionNotes != null ? !nextActionNotes.equals(that.nextActionNotes) : that.nextActionNotes != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = number;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (bannerImageName != null ? bannerImageName.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (lastCheckedDate != null ? lastCheckedDate.hashCode() : 0);
        result = 31 * result + (nextActionType != null ? nextActionType.hashCode() : 0);
        result = 31 * result + (nextActionNotes != null ? nextActionNotes.hashCode() : 0);
        return result;
    }

    @Basic
    @Column(name = "sortKey", nullable = true)
    public Integer getSortKey() {
        return sortKey;
    }

    public void setSortKey(Integer sortKey) {
        this.sortKey = sortKey;
    }

    @Basic
    @Column(name = "userName", nullable = true, length = 45)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "password", nullable = true, length = 45)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "bannerRequired", nullable = false)
    public byte getBannerRequired() {
        return bannerRequired;
    }

    public void setBannerRequired(byte bannerRequired) {
        this.bannerRequired = bannerRequired;
    }
}
