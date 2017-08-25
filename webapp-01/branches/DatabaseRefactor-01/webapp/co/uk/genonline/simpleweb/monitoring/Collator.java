package co.uk.genonline.simpleweb.monitoring;

import co.uk.genonline.simpleweb.controller.WebLogger;
import co.uk.genonline.simpleweb.monitoring.collectables.Collectable;
import co.uk.genonline.simpleweb.monitoring.collectables.CollectableImpl;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by thomassecondary on 31/05/2017.
 */

@XmlRootElement(name = "monitor")
@XmlAccessorType(XmlAccessType.NONE)
public class Collator {
    private WebLogger logger = new WebLogger();
    private static Collator ourInstance = new Collator();
    private Map<String, Collectable> collectors;

    public static Collator getInstance() {
        return ourInstance;
    }

    private Collator() {
        collectors = new HashMap<>();
    }

    /**
     * Adds supplied collector to collated list, but if already present, simply overwrites value.
     *
     * Collectors must be named uniquely. So by checking whether a named collector already exists we know whether
     * the supplied one is new or an updated one.  If there is already one there, simply update the value, as that
     * is the only changeable aspect.
     *
     * @param collector
     */
    public void addOrUpdateCollector(Collectable collector) {
        String key = calculateKey(collector);
        logger.info("Attempting to add (or update) collector with name <%s>", key);
        if (collectors.containsKey(key)) {
            logger.warn("Attempt to add collector <%s> which already exists", key);
        } else {
            logger.info("Adding collector with name <%s>", key);
            collectors.put(key, collector);
        }
    }

    public String calculateKey(Collectable collector) {
        return calculateKey(collector.getCategory(), collector.isSummary(), collector.getName());
    }

    public String calculateKey(String category, Boolean isSummary, String name) {
        return category + "-" + (isSummary ? "Summary" : name);
    }

    public Collectable getCollector(String key) {
        return collectors.get(key);
    }

    @XmlElement(name="item")
    public List<Collectable> getCollectorsAsList() {
        return new ArrayList<>(collectors.values());
    }
}
