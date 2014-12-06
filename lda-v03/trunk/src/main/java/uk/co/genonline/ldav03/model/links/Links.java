package uk.co.genonline.ldav03.model.links;

import uk.co.genonline.ldav03.controller.UrlMapping;
import uk.co.genonline.ldav03.model.entities.LinksEntity;
import uk.co.genonline.ldav03.web.LinkData;

/**
 * Created by thomassecondary on 02/12/2014.
 */
public class Links {
    LinksEntity linksEntity;
    LinkData linkData;
    public Links(LinksEntity linksEntity) {
        this.linksEntity = linksEntity;
        this.linkData = new LinkData(linksEntity.getLinksName(), linksEntity.getLinksShortName(), linksEntity.getLinksLongName(), UrlMapping.LINKS_CLASS_URL_MAPPING);
    }

    public LinkData getLinkData() {
        return this.linkData;
    }

    public String getLinkBannerType() {
        return linksEntity.getLinkBannerType();
    }

    public String getLinkBannerImageName() {
        return linksEntity.getLinkBannerImageName();
    }

    public String getLinkTargetUrl() {
        return linksEntity.getLinkTargetUrl();
    }

    public String getLinkCategory() {
        return linksEntity.getLinkCategory();
    }

    public int getLinkSortOrder() {
        return linksEntity.getLinkSortOrder();
    }

    public String toString() {
        return String.format("Links: <name:%s> <shortname:%s> <longname:%s>", linkData.getName(), linkData.getShortName(), linkData.getLongName());
    }
}
