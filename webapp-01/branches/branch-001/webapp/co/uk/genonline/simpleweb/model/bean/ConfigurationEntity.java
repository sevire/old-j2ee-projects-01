package co.uk.genonline.simpleweb.model.bean;

import javax.persistence.*;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 26/10/2013
 * Time: 13:00
 * To change this template use File | Settings | File Templates.
 */
@Table(name = "Configuration", schema = "", catalog = "lda_v02")
@Entity
public class ConfigurationEntity {
    private String name;
    private String value;
    private int id;

    @Column(name = "name")
    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "value")
    @Basic
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigurationEntity that = (ConfigurationEntity) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        return !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    public String toString() {
        return "ScreenEntity:" + "name:" + name + "value:" + value;
    }

    @Column(name = "id")
    @Basic
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
