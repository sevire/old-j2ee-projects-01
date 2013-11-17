package co.uk.genonline.simpleweb.model.bean;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 24/10/2013
 * Time: 17:31
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Screen_type", schema = "", catalog = "lda_v02")
@Entity
public class ScreenTypeEntity {
    private int id;
    private String category;

    @Column(name = "id")
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "category")
    @Basic
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScreenTypeEntity that = (ScreenTypeEntity) o;

        if (id != that.id) return false;
        if (category != null ? !category.equals(that.category) : that.category != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
