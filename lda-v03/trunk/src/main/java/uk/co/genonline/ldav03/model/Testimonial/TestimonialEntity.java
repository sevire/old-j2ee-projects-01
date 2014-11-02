package uk.co.genonline.ldav03.model.Testimonial;

import javax.persistence.*;

/**
 * Created by thomassecondary on 27/10/2014.
 */
@Entity
@Table(name = "Testimonial", schema = "", catalog = "lda_v03_01")
public class TestimonialEntity {
    private String testimonialName;
    private String testimonialSlaveName;
    private int testimonialSortKey;
    private String testimonialContent;
    private String testimonialLongName;
    private String testimonialShortName;

    @Id
    @Column(name = "TestimonialName", nullable = false, insertable = true, updatable = true, length = 20)
    public String getTestimonialName() {
        return testimonialName;
    }

    public void setTestimonialName(String testimonialName) {
        this.testimonialName = testimonialName;
    }

    @Basic
    @Column(name = "TestimonialSlaveName", nullable = false, insertable = true, updatable = true, length = 50)
    public String getTestimonialSlaveName() {
        return testimonialSlaveName;
    }

    public void setTestimonialSlaveName(String testimonialSlaveName) {
        this.testimonialSlaveName = testimonialSlaveName;
    }

    @Basic
    @Column(name = "TestimonialSortKey", nullable = false, insertable = true, updatable = true)
    public int getTestimonialSortKey() {
        return testimonialSortKey;
    }

    public void setTestimonialSortKey(int testimonialSortKey) {
        this.testimonialSortKey = testimonialSortKey;
    }

    @Basic
    @Column(name = "TestimonialContent", nullable = false, insertable = true, updatable = true, length = 2147483647)
    public String getTestimonialContent() {
        return testimonialContent;
    }

    public void setTestimonialContent(String testimonialContent) {
        this.testimonialContent = testimonialContent;
    }

    @Basic
    @Column(name = "TestimonialLongName", nullable = false, insertable = true, updatable = true, length = 50)
    public String getTestimonialLongName() {
        return testimonialLongName;
    }

    public void setTestimonialLongName(String testimonialLongName) {
        this.testimonialLongName = testimonialLongName;
    }

    @Basic
    @Column(name = "TestimonialShortName", nullable = false, insertable = true, updatable = true, length = 50)
    public String getTestimonialShortName() {
        return testimonialShortName;
    }

    public void setTestimonialShortName(String testimonialShortName) {
        this.testimonialShortName = testimonialShortName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TestimonialEntity that = (TestimonialEntity) o;

        if (testimonialSortKey != that.testimonialSortKey) return false;
        if (testimonialContent != null ? !testimonialContent.equals(that.testimonialContent) : that.testimonialContent != null)
            return false;
        if (testimonialLongName != null ? !testimonialLongName.equals(that.testimonialLongName) : that.testimonialLongName != null)
            return false;
        if (testimonialName != null ? !testimonialName.equals(that.testimonialName) : that.testimonialName != null)
            return false;
        if (testimonialShortName != null ? !testimonialShortName.equals(that.testimonialShortName) : that.testimonialShortName != null)
            return false;
        if (testimonialSlaveName != null ? !testimonialSlaveName.equals(that.testimonialSlaveName) : that.testimonialSlaveName != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = testimonialName != null ? testimonialName.hashCode() : 0;
        result = 31 * result + (testimonialSlaveName != null ? testimonialSlaveName.hashCode() : 0);
        result = 31 * result + testimonialSortKey;
        result = 31 * result + (testimonialContent != null ? testimonialContent.hashCode() : 0);
        result = 31 * result + (testimonialLongName != null ? testimonialLongName.hashCode() : 0);
        result = 31 * result + (testimonialShortName != null ? testimonialShortName.hashCode() : 0);
        return result;
    }
}
