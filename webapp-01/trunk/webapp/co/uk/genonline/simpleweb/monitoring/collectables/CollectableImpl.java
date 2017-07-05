package co.uk.genonline.simpleweb.monitoring.collectables;

import co.uk.genonline.simpleweb.monitoring.CollectableCategory;
import co.uk.genonline.simpleweb.web.gallery.GalleryStatus;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import java.util.Map;

/**
 * Created by thomassecondary on 06/06/2017.
 */

public abstract class CollectableImpl extends Collectable {
    private final CollectableCategory category ;
    private final String name;
    private final boolean summaryFlag;

    public CollectableImpl(CollectableCategory category, String name, boolean summaryFlag) {

        this.category = category;
        this.name = name;
        this.summaryFlag = summaryFlag;
    }

    @Override
    public String getCategory() {
        return category.getCategoryDescription();
    }

    @Override
    public boolean isSummary() {
        return summaryFlag;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    @XmlElement
    public abstract CollectableDataObject getData();

    public String toString() {
        return String.format("%s (%b) : %s", getCategory(), isSummary(), getData().getDisplayData().toString());
    }
}
