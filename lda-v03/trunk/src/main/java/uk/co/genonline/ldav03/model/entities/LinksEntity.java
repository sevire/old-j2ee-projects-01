package uk.co.genonline.ldav03.model.entities;

import javax.persistence.*;

/**
 * Created by thomassecondary on 02/12/2014.
 */
@Entity
@Table(name = "Links", schema = "", catalog = "lda_v03_01")
public class LinksEntity {
    private String linksName;
    private String linksShortName;
    private String linksLongName;
    private String linkBannerType;
    private String linkBannerImageName;
    private String linkTargetUrl;
    private String linkCategory;
    private Integer linkSortOrder;

    @Id
    @Column(name = "linksName", nullable = false, insertable = true, updatable = true, length = 20)
    public String getLinksName() {
        return linksName;
    }

    public void setLinksName(String linksName) {
        this.linksName = linksName;
    }

    @Basic
    @Column(name = "linksShortName", nullable = true, insertable = true, updatable = true, length = 50)
    public String getLinksShortName() {
        return linksShortName;
    }

    public void setLinksShortName(String linksShortName) {
        this.linksShortName = linksShortName;
    }

    @Basic
    @Column(name = "linksLongName", nullable = true, insertable = true, updatable = true, length = 50)
    public String getLinksLongName() {
        return linksLongName;
    }

    public void setLinksLongName(String linksLongName) {
        this.linksLongName = linksLongName;
    }

    @Basic
    @Column(name = "linkBannerType", nullable = true, insertable = true, updatable = true, length = 20)
    public String getLinkBannerType() {
        return linkBannerType;
    }

    public void setLinkBannerType(String linkBannerType) {
        this.linkBannerType = linkBannerType;
    }

    @Basic
    @Column(name = "linkBannerImageName", nullable = true, insertable = true, updatable = true, length = 50)
    public String getLinkBannerImageName() {
        return linkBannerImageName;
    }

    public void setLinkBannerImageName(String linkBannerImageName) {
        this.linkBannerImageName = linkBannerImageName;
    }

    @Basic
    @Column(name = "linkTargetUrl", nullable = true, insertable = true, updatable = true, length = 50)
    public String getLinkTargetUrl() {
        return linkTargetUrl;
    }

    public void setLinkTargetUrl(String linkTargetUrl) {
        this.linkTargetUrl = linkTargetUrl;
    }

    @Basic
    @Column(name = "linkCategory", nullable = false, insertable = true, updatable = true, length = 20)
    public String getLinkCategory() {
        return linkCategory;
    }

    public void setLinkCategory(String linkCategory) {
        this.linkCategory = linkCategory;
    }

    @Basic
    @Column(name = "linkSortOrder", nullable = true, insertable = true, updatable = true)
    public Integer getLinkSortOrder() {
        return linkSortOrder;
    }

    public void setLinkSortOrder(Integer linkSortOrder) {
        this.linkSortOrder = linkSortOrder;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LinksEntity that = (LinksEntity) o;

        if (linkBannerImageName != null ? !linkBannerImageName.equals(that.linkBannerImageName) : that.linkBannerImageName != null)
            return false;
        if (linkBannerType != null ? !linkBannerType.equals(that.linkBannerType) : that.linkBannerType != null)
            return false;
        if (linkCategory != null ? !linkCategory.equals(that.linkCategory) : that.linkCategory != null) return false;
        if (linkSortOrder != null ? !linkSortOrder.equals(that.linkSortOrder) : that.linkSortOrder != null)
            return false;
        if (linkTargetUrl != null ? !linkTargetUrl.equals(that.linkTargetUrl) : that.linkTargetUrl != null)
            return false;
        if (linksLongName != null ? !linksLongName.equals(that.linksLongName) : that.linksLongName != null)
            return false;
        if (linksName != null ? !linksName.equals(that.linksName) : that.linksName != null) return false;
        if (linksShortName != null ? !linksShortName.equals(that.linksShortName) : that.linksShortName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = linksName != null ? linksName.hashCode() : 0;
        result = 31 * result + (linksShortName != null ? linksShortName.hashCode() : 0);
        result = 31 * result + (linksLongName != null ? linksLongName.hashCode() : 0);
        result = 31 * result + (linkBannerType != null ? linkBannerType.hashCode() : 0);
        result = 31 * result + (linkBannerImageName != null ? linkBannerImageName.hashCode() : 0);
        result = 31 * result + (linkTargetUrl != null ? linkTargetUrl.hashCode() : 0);
        result = 31 * result + (linkCategory != null ? linkCategory.hashCode() : 0);
        result = 31 * result + (linkSortOrder != null ? linkSortOrder.hashCode() : 0);
        return result;
    }
}
