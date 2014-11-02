package uk.co.genonline.ldav03.model.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by thomassecondary on 17/09/2014.
 */
@Entity
@Table(name = "User", schema = "", catalog = "lda_v03_01")
//ToDo: Work out why auto-generate didn't include Serializable (did for MistressEntity)
public class UserEntity implements Serializable {
    private String userName;
    private String password;
    private String accessType;

    @Id
    @Column(name = "UserName", nullable = false, insertable = true, updatable = true, length = 20)
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Basic
    @Column(name = "Password", nullable = false, insertable = true, updatable = true, length = 20)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Basic
    @Column(name = "AccessType", nullable = false, insertable = true, updatable = true, length = 10)
    public String getAccessType() {
        return accessType;
    }

    public void setAccessType(String accessType) {
        this.accessType = accessType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserEntity that = (UserEntity) o;

        if (accessType != null ? !accessType.equals(that.accessType) : that.accessType != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;
        if (userName != null ? !userName.equals(that.userName) : that.userName != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (accessType != null ? accessType.hashCode() : 0);
        return result;
    }
}
