package uk.co.genonline.ldav03.model.entities;

import javax.persistence.*;
import java.util.Collection;

/**
 *
 */
@Entity
@Table(name = "Mistress", schema = "", catalog = "lda_v03_01")
public class MistressEntity {
    private String mistressName;
    private String mistressContent;
    private String mistressLongName;
    private String mistressShortName;
    private String mistressContentType;
    private byte galleryFlag;
    private Collection<TestimonialEntity> testimonialsByMistressName;

    @Id
    @Column(name = "mistressName", nullable = false, insertable = true, updatable = true, length = 20)
    public String getMistressName() {
        return mistressName;
    }

    public void setMistressName(String mistressName) {
        this.mistressName = mistressName;
    }

    @Basic
    @Column(name = "mistressContent", nullable = true, insertable = true, updatable = true, length = 2147483647)
    public String getMistressContent() {
        return mistressContent;
    }

    public void setMistressContent(String mistressContent) {
        this.mistressContent = mistressContent;
    }

    @Basic
    @Column(name = "mistressLongName", nullable = true, insertable = true, updatable = true, length = 50)
    public String getMistressLongName() {
        return mistressLongName;
    }

    public void setMistressLongName(String mistressLongName) {
        this.mistressLongName = mistressLongName;
    }

    @Basic
    @Column(name = "mistressShortName", nullable = true, insertable = true, updatable = true, length = 50)
    public String getMistressShortName() {
        return mistressShortName;
    }

    public void setMistressShortName(String mistressShortName) {
        this.mistressShortName = mistressShortName;
    }

    @Basic
    @Column(name = "mistressContentType", nullable = true, insertable = true, updatable = true, length = 20)
    public String getMistressContentType() {
        return mistressContentType;
    }

    public void setMistressContentType(String mistressContentType) {
        this.mistressContentType = mistressContentType;
    }

    @Basic
    @Column(name = "galleryFlag", nullable = false, insertable = true, updatable = true)
    public byte getGalleryFlag() {
        return galleryFlag;
    }

    public void setGalleryFlag(byte galleryFlag) {
        this.galleryFlag = galleryFlag;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MistressEntity that = (MistressEntity) o;

        if (galleryFlag != that.galleryFlag) return false;
        if (mistressContent != null ? !mistressContent.equals(that.mistressContent) : that.mistressContent != null)
            return false;
        if (mistressContentType != null ? !mistressContentType.equals(that.mistressContentType) : that.mistressContentType != null)
            return false;
        if (mistressLongName != null ? !mistressLongName.equals(that.mistressLongName) : that.mistressLongName != null)
            return false;
        if (mistressName != null ? !mistressName.equals(that.mistressName) : that.mistressName != null) return false;
        if (mistressShortName != null ? !mistressShortName.equals(that.mistressShortName) : that.mistressShortName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = mistressName != null ? mistressName.hashCode() : 0;
        result = 31 * result + (mistressContent != null ? mistressContent.hashCode() : 0);
        result = 31 * result + (mistressLongName != null ? mistressLongName.hashCode() : 0);
        result = 31 * result + (mistressShortName != null ? mistressShortName.hashCode() : 0);
        result = 31 * result + (mistressContentType != null ? mistressContentType.hashCode() : 0);
        result = 31 * result + (int) galleryFlag;
        return result;
    }

    @OneToMany(mappedBy = "mistressByMistressName")
    public Collection<TestimonialEntity> getTestimonialsByMistressName() {
        return testimonialsByMistressName;
    }

    public void setTestimonialsByMistressName(Collection<TestimonialEntity> testimonialsByMistressName) {
        this.testimonialsByMistressName = testimonialsByMistressName;
    }
}
