package uk.co.genonline.ldav03.model.Chamber;

import org.markdown4j.Markdown4jProcessor;

import java.io.IOException;

/**
 * Created by thomassecondary on 26/10/2014.
 */
public class ChamberInformation {
    ChamberInformationEntity chamberInformationEntity;

    public ChamberInformation(ChamberInformationEntity chamberInformationEntity) {
        this.chamberInformationEntity = chamberInformationEntity;
    }

    public ChamberInformationEntity getChamberInformationEntity() {
        return this.chamberInformationEntity;
    }

    public String getChamberInformationName() {
        return chamberInformationEntity.getChamberInformationName();
    }

    public String getChamberInformationContent() {
        return chamberInformationEntity.getChamberInformationContent();
    }

    public String getChamberInformationContentDecoded() {
        String outputHTML;
        Markdown4jProcessor processor = new Markdown4jProcessor();
        try {
            outputHTML = processor.process(getChamberInformationContent());
        } catch (IOException e) {
            outputHTML = "Couldn't parse Markdown text";
            e.printStackTrace();
        }
        return outputHTML;
    }

    public String getChamberInformationLongName() {
        if (chamberInformationEntity.getChamberInformationLongName() == null || chamberInformationEntity.getChamberInformationLongName().isEmpty()) {
            return getChamberInformationShortName();
        } else {
            return chamberInformationEntity.getChamberInformationLongName();
        }
    }

    public String getChamberInformationShortName() {
        if (chamberInformationEntity.getChamberInformationShortName() == null || chamberInformationEntity.getChamberInformationShortName().isEmpty()) {
            return getChamberInformationName();
        } else {
            return chamberInformationEntity.getChamberInformationShortName();
        }
    }

    public byte getGalleryFlag() {
        return chamberInformationEntity.getGalleryFlag();
    }

}
