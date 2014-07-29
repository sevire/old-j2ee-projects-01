package uk.co.genonline.springapp05.model;

/**
 * Acts as a wrapper for the MistressEntity class, in order to allow some pre-processing of requests.
 *
 * Example: If the long name isn't specified, then the short name should be returned.  This allows the user to avoid
 * having to enter the same data twice or more if the two names aren't distinct.
 *
 * Not sure whether this should be a sub-class or not.  Will start off with it being one as so much is common.
 */
public class Mistress {
    MistressEntity mistressEntity;

    public Mistress(MistressEntity mistressEntity) {
        this.mistressEntity = mistressEntity;
    }
    public String getMistressName() {
        return mistressEntity.getMistressName();
    }

    public String getMistressContent() {
        return mistressEntity.getMistressContent();
    }

    public String getMistressContentType() {
        return mistressEntity.getMistressContentType();
    }

    public String getMistressLongName() {
        if (mistressEntity.getMistressLongName() == null || mistressEntity.getMistressLongName().isEmpty()) {
            return getMistressShortName();
        } else {
            return mistressEntity.getMistressLongName();
        }
    }

    public String getMistressShortName() {
        if (mistressEntity.getMistressShortName() == null || mistressEntity.getMistressShortName().isEmpty()) {
            return getMistressName();
        } else {
            return mistressEntity.getMistressShortName();
        }
    }
}
