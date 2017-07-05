package co.uk.genonline.simpleweb.monitoring.collectables;

import org.hibernate.internal.SessionFactoryImpl;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlTransient;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by thomassecondary on 20/06/2017.
 */
public class MonitoringDatabaseSummary extends CollectableDataObject {

    private final SessionFactoryImpl factory;
    public MonitoringDatabaseSummary(SessionFactoryImpl factory) {
        this.factory = factory;
    }

    @XmlElement
    @Override
    public Map<String, String> getDisplayData() {
        return Collections.unmodifiableMap(new HashMap<String, String>() {
            {
                String databaseName = (String)factory.getProperties().get("hibernate.connection.url");
                Long connectCount = factory.getStatistics().getConnectCount();

                put("Database Name", databaseName);
                put("Connect Count (not connection count)", connectCount.toString());
            }
        });
    }
}
