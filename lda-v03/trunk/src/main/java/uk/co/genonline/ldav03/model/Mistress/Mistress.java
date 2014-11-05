package uk.co.genonline.ldav03.model.Mistress;

import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;

/**
 * Acts as a wrapper for the MistressEntity class, in order to allow some pre-processing of requests.
 *
 * The MistressEntity class is automatically generated from the DB Schema so it is cleaner if it isn't modified.
 * The Mistress class allows more sophisticated operations such as returning the short name as the long name if the
 * long name isn't populated (This allows a user to populate the minimum number of fields).
 *
 */
public class Mistress {
    MistressEntity mistressEntity;

    public Mistress(MistressEntity mistressEntity) {
        this.mistressEntity = mistressEntity;
    }

    /**
     *
     * @return
     */
    public MistressEntity getMistressEntity() {
        return mistressEntity;
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

    public String getMistressContentDecoded() {
        String outputHTML;
        Markdown4jProcessor processor = new Markdown4jProcessor();
        try {
            outputHTML = processor.process(getMistressContent());
        } catch (IOException e) {
            outputHTML = "Couldn't parse Markdown text";
            e.printStackTrace();
        }
        return outputHTML;
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

    public boolean isGalleryFlag() {
        return mistressEntity.getGalleryFlag() != 0;
    }

    public String toString() {
        return String.format("Mistress: <name:%s> <shortname:%s> <longname:%s>", getMistressName(),getMistressShortName(),getMistressLongName());
    }
}
