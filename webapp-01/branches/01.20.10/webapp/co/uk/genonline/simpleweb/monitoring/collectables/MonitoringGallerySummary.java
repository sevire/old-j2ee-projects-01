package co.uk.genonline.simpleweb.monitoring.collectables;

import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomassecondary on 20/06/2017.
 */
public class MonitoringGallerySummary extends CollectableDataObject {
    private Integer numGalleries;

    public MonitoringGallerySummary(Integer numGalleries) {
        this.numGalleries = numGalleries;
    }

    public Integer getNumGalleries() {
        return numGalleries;
    }

    public void setNumGalleries(Integer numGalleries) {
        this.numGalleries = numGalleries;
    }

    @XmlElement
    @Override
    public Map<String, String> getDisplayData() {
        return Collections.unmodifiableMap(new HashMap<String, String>() {
            {
                put("Number of Galleries",numGalleries.toString());
            }
        });
    }
}
