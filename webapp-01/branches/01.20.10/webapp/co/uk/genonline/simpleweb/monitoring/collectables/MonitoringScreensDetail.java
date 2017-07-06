package co.uk.genonline.simpleweb.monitoring.collectables;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Detail Data For Screens. Includes:
 * - Screen Name
 * - Number of requests for screen
 * - Time of last request
 * - Time of last error
 * - Last error message
 */
public class MonitoringScreensDetail extends CollectableDataObject {
    private final String screenName;
    private final Integer numRequestsSinceStartup;
    private final LocalDateTime timeOfLastRequest;
    private final String lastErrorMessage;

    public MonitoringScreensDetail(
            String screenName,
            Integer numRequestsSinceStartup,
            LocalDateTime timeOfLastRequest,
            String lastErrorMessage) {
        this.screenName = screenName;
        this.numRequestsSinceStartup = numRequestsSinceStartup;
        this.timeOfLastRequest = timeOfLastRequest;
        this.lastErrorMessage = lastErrorMessage;
    }

    @XmlElement
    @Override
    public Map<String, String> getDisplayData() {
        return Collections.unmodifiableMap(new HashMap<String, String>() {
            {
                put("Screen Name",screenName.toString());
                put("Number Of Requests Since Startup",numRequestsSinceStartup.toString());
                put("Time Of Last Request",timeOfLastRequest.toString());
                put("Last Error Message",lastErrorMessage.toString());
            }
        });
    }
}
