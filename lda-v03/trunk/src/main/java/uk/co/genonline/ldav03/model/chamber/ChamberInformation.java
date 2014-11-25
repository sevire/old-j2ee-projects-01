package uk.co.genonline.ldav03.model.chamber;

import org.markdown4j.Markdown4jProcessor;
import uk.co.genonline.ldav03.controller.UrlMapping;
import uk.co.genonline.ldav03.model.PageData;
import uk.co.genonline.ldav03.model.entities.ChamberInformationEntity;
import uk.co.genonline.ldav03.web.LinkData;

import java.io.IOException;

/**
 * Created by thomassecondary on 26/10/2014.
 */
public class ChamberInformation implements PageData {
    ChamberInformationEntity chamberInformationEntity;
    LinkData linkData;

    public ChamberInformation(ChamberInformationEntity chamberInformationEntity) {
        this.chamberInformationEntity = chamberInformationEntity;
        this.linkData = new LinkData(chamberInformationEntity.getChamberInformationName(), chamberInformationEntity.getChamberInformationShortName(), chamberInformationEntity.getChamberInformationLongName(), UrlMapping.CHAMBER_CLASS_URL_MAPPING);;
    }

    public LinkData getLinkData() {
        return linkData;
    }

    public ChamberInformationEntity getDataEntity() {
        return this.chamberInformationEntity;
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

    public boolean isGalleryFlag() {
        return chamberInformationEntity.getGalleryFlag() != 0;
    }

}
