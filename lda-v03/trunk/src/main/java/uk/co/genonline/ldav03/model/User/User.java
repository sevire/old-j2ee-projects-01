package uk.co.genonline.ldav03.model.User;

/**
 * Created by thomassecondary on 17/09/2014.
 */
public class User {
    private final UserEntity userEntity;

    public String getUserName() {
        return userEntity.getUserName();
    }

    public String getPassword() {
        return userEntity.getPassword();
    }

    public String getAccessType() {
        return userEntity.getAccessType();
    }

    enum UserAccessTypes {
        READ,
        UPDATE,
        ADD;

        public boolean isSet(String accessString) {
            return accessString.substring(this.ordinal()).equals("Y");
        }
    }

    public User(UserEntity userEntity) {
        this.userEntity = userEntity;
    };

    public boolean isRead(String accessString) {
        return UserAccessTypes.READ.isSet(userEntity.getAccessType());
    }

    public boolean isWrite(String accessString) {
        return UserAccessTypes.UPDATE.isSet(userEntity.getAccessType());
    }

    public boolean isAdd(String accessString) {
        return UserAccessTypes.ADD.isSet(userEntity.getAccessType());
    }
}
