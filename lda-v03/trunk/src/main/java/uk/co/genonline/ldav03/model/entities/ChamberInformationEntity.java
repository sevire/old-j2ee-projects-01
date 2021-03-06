package uk.co.genonline.ldav03.model.entities;

import javax.persistence.*;

/**
 * Created by thomassecondary on 07/11/2014.
 */
@Entity
@Table(name = "ChamberInformation", schema = "", catalog = "lda_v03_01")
public class ChamberInformationEntity {
    private String chamberInformationName;
    private String chamberInformationContent;
    private String chamberInformationLongName;
    private String chamberInformationShortName;
    private Byte galleryFlag;

    @Id
    @Column(name = "chamberInformationName", nullable = false, insertable = true, updatable = true, length = 20)
    public String getChamberInformationName() {
        return chamberInformationName;
    }

    public void setChamberInformationName(String chamberInformationName) {
        this.chamberInformationName = chamberInformationName;
    }

    @Basic
    @Column(name = "chamberInformationContent", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getChamberInformationContent() {
        return chamberInformationContent;
    }

    public void setChamberInformationContent(String chamberInformationContent) {
        this.chamberInformationContent = chamberInformationContent;
    }

    @Basic
    @Column(name = "chamberInformationLongName", nullable = true, insertable = true, updatable = true, length = 50)
    public String getChamberInformationLongName() {
        return chamberInformationLongName;
    }

    public void setChamberInformationLongName(String chamberInformationLongName) {
        this.chamberInformationLongName = chamberInformationLongName;
    }

    @Basic
    @Column(name = "chamberInformationShortName", nullable = true, insertable = true, updatable = true, length = 50)
    public String getChamberInformationShortName() {
        return chamberInformationShortName;
    }

    public void setChamberInformationShortName(String chamberInformationShortName) {
        this.chamberInformationShortName = chamberInformationShortName;
    }

    @Basic
    @Column(name = "galleryFlag", nullable = true, insertable = true, updatable = true)
    public Byte getGalleryFlag() {
        return galleryFlag;
    }

    public void setGalleryFlag(Byte galleryFlag) {
        this.galleryFlag = galleryFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChamberInformationEntity that = (ChamberInformationEntity) o;

        if (chamberInformationContent != null ? !chamberInformationContent.equals(that.chamberInformationContent) : that.chamberInformationContent != null)
            return false;
        if (chamberInformationLongName != null ? !chamberInformationLongName.equals(that.chamberInformationLongName) : that.chamberInformationLongName != null)
            return false;
        if (chamberInformationName != null ? !chamberInformationName.equals(that.chamberInformationName) : that.chamberInformationName != null)
            return false;
        if (chamberInformationShortName != null ? !chamberInformationShortName.equals(that.chamberInformationShortName) : that.chamberInformationShortName != null)
            return false;
        if (galleryFlag != null ? !galleryFlag.equals(that.galleryFlag) : that.galleryFlag != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = chamberInformationName != null ? chamberInformationName.hashCode() : 0;
        result = 31 * result + (chamberInformationContent != null ? chamberInformationContent.hashCode() : 0);
        result = 31 * result + (chamberInformationLongName != null ? chamberInformationLongName.hashCode() : 0);
        result = 31 * result + (chamberInformationShortName != null ? chamberInformationShortName.hashCode() : 0);
        result = 31 * result + (galleryFlag != null ? galleryFlag.hashCode() : 0);
        return result;
    }
}
