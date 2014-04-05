package co.uk.genonline.simpleweb.model.bean;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 11/06/2012
 * Time: 12:41
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Screen_type", schema = "", catalog = "lda_dev")
@Entity
public class ScreenType {
    private int id;

    @Column(name = "id", nullable = false, insertable = true, updatable = true, length = 10, precision = 0)
    @Id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String category;

    @Column(name = "category", nullable = false, insertable = true, updatable = true, length = 45, precision = 0)
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

        ScreenType that = (ScreenType) o;

        if (id != that.id) return false;
        return !(category != null ? !category.equals(that.category) : that.category != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (category != null ? category.hashCode() : 0);
        return result;
    }
}
