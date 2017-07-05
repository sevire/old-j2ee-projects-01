package co.uk.genonline.simpleweb.monitoring.collectables;

import javax.xml.bind.annotation.XmlElement;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Summary Data For Screens. Includes:
 * - Total number of Enabled Screens
 * - Number of disabled screens
 * - Number of (enabled) screens for each screenType
 * - Number of screens for each displayType
 * - Total number of requests since startup
 */
public class MonitoringScreensSummary extends CollectableDataObject {
    private final Integer numEnabledScreens;
    private final Integer numDisabledScreens;
    private final Integer numRequestsSinceStartup;
    private final Map<String, Integer> screenCountByScreenType;
    private final Map<String, Integer> screenCountByScreenDisplayType;

    public MonitoringScreensSummary(
            Integer numEnabledScreens,
            Integer numDisabledScreens,
            Integer numRequestsSinceStartup,
            Map<String, Integer> screenCountByScreenType,
            Map<String, Integer> screenCountByScreenDisplayType) {
        this.numEnabledScreens = numEnabledScreens;
        this.numDisabledScreens = numDisabledScreens;
        this.numRequestsSinceStartup = numRequestsSinceStartup;
        this.screenCountByScreenType = screenCountByScreenType;
        this.screenCountByScreenDisplayType = screenCountByScreenDisplayType;
    }

    @XmlElement
    @Override
    public Map<String, String> getDisplayData() {
        return Collections.unmodifiableMap(new HashMap<String, String>() {
            {
                put("Number Of Enabled Screens",numEnabledScreens.toString());
                put("Number of Disabled Screens",numDisabledScreens.toString());
                put("Number of Requests Since Startup", numRequestsSinceStartup.toString());
                for (String screenType : screenCountByScreenType.keySet()) {
                    put(String.format("Number of (enabled) screens of type <%s>", screenType), screenCountByScreenType.get(screenType).toString());
                }
                for (String screenDisplayType : screenCountByScreenDisplayType.keySet()) {
                    put(String.format("Number of (enabled) screens of display type <%s>", screenDisplayType), screenCountByScreenType.get(screenDisplayType).toString());
                }
            }
        });
    }
}
