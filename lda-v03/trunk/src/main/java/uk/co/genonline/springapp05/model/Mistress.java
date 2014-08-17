package uk.co.genonline.springapp05.model;

/**
 * Acts as a wrapper for the MistressEntity class, in order to allow some pre-processing of requests.
 *
 * Example: If the long name isn't specified, then the short name should be returned.  This allows the user to avoid
 * having to enter the same data twice or more if the two names aren't distinct.
 *
 */
public class Mistress {
    MistressEntity mistressEntity;

    public Mistress(MistressEntity mistressEntity) {
        this.mistressEntity = mistressEntity;
    }

    /**
     * Just passes through the mistress name (primary key) field
     *
     * @return Mistress Name field.
     */
    public String getMistressName() {
        return mistressEntity.getMistressName();
    }

    /**
     * Just passes through the content data with no change
     * @return Mistress Content.
     */
    public String getMistressContent() {
        return mistressEntity.getMistressContent();
    }


    public String getMistressContentType() {
        return mistressEntity.getMistressContentType();
    }

    /**
     * If the long name isn't populated, use the short name instead.
     * @return
     */
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

    public String toString() {
        return String.format("Mistress: <name:%s> <shortname:%s> <longname:%s>", getMistressName(),getMistressShortName(),getMistressLongName());
    }
}
