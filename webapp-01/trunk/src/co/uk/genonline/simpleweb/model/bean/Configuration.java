package co.uk.genonline.simpleweb.model.bean;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 01/04/2013
 * Time: 11:50
 * To change this template use File | Settings | File Templates.
 */
@Entity
public class Configuration {
    private String name;

    @javax.persistence.Column(name = "name", nullable = false, insertable = true, updatable = true, length = 50, precision = 0)
    @Id
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String value;

    @javax.persistence.Column(name = "value", nullable = true, insertable = true, updatable = true, length = 255, precision = 0)
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

        Configuration that = (Configuration) o;

        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (value != null ? !value.equals(that.value) : that.value != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
