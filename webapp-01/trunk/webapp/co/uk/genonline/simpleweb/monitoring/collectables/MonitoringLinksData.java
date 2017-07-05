package co.uk.genonline.simpleweb.monitoring.collectables;

import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomassecondary on 20/06/2017.
 */
public class MonitoringLinksData extends CollectableDataObject {
    private Integer numLiveLinks;
    private Integer numInactiveLinks;

    public MonitoringLinksData(Integer numLiveLinks, Integer numInactiveLinks) {
        this.numLiveLinks = numLiveLinks;
        this.numInactiveLinks = numInactiveLinks;
    }

    public Integer getNumLiveLinks() {
        return numLiveLinks;
    }

    public void setNumLiveLinks(Integer numLiveLinks) {
        this.numLiveLinks = numLiveLinks;
    }

    public Integer getNumInactiveLinks() {
        return numInactiveLinks;
    }

    public void setNumInactiveLinks(Integer numInactiveLinks) {
        this.numInactiveLinks = numInactiveLinks;
    }

    @XmlElement
    @Override
    public Map<String, String> getDisplayData() {
        return Collections.unmodifiableMap(new HashMap<String, String>() {
            {
                put("Number of Live Links",numLiveLinks.toString());
                put("Number of Inactive Links",numInactiveLinks.toString());
            }
        });
    }
}
