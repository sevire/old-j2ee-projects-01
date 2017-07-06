package co.uk.genonline.simpleweb.monitoring.collectables;

import co.uk.genonline.simpleweb.monitoring.CollectableCategory;
import co.uk.genonline.simpleweb.web.gallery.GalleryManagerDefault;
import co.uk.genonline.simpleweb.web.gallery.GalleryStatus;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Map;

/**
 * Implements functionality to maintain or calculate a particular measure as part of Monitoring functionality.
 */

// @SeeAlso required for JAXB to find and parse sub-classes.  Don't know why but it's necessary.
@XmlSeeAlso({
        GalleryStatus.class,
        MonitoringDatabaseSummary.class,
        MonitoringLinksData.class,
        MonitoringGallerySummary.class,
        MonitoringScreensDetail.class,
        MonitoringScreensSummary.class
})
public abstract class Collectable {

    @XmlElement
    public abstract CollectableDataObject getData();

    @XmlElement
    public abstract String getCategory();

    /**
     * Name is used to distinguish between detail records for the same category.  Must be unique as is used as key into
     * Map of collectible objects.
     *
     * @return
     */
    @XmlElement
    public abstract String getName();

    /**
     * If set then this is a single summary data item for this category, otherwise one of many detailed items for this
     * category
     *
     * @return
     */
    @XmlElement
    public abstract boolean isSummary();
}
