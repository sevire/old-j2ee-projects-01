package co.uk.genonline.simpleweb.monitoring.collectables;

import java.util.Map;

/**
 * Used to encapsulate data to be tracked within a particular monitoring object.  In order to allow easy display of
 * data, the method getDisplayData() returns a list (Map) of labels and string values which will be converted to
 * XML (possibly JSON) and returned as part of a REST service.
 */
public abstract class CollectableDataObject {
    abstract public Map<String, String> getDisplayData();
}
