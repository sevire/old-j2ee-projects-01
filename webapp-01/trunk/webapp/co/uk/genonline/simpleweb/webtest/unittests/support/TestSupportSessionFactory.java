package unittests.support;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.File;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by thomassecondary on 15/05/15.
 */
public class TestSupportSessionFactory {
    static File hibernateConfig = new File("/Users/thomassecondary/Projects/webapp-01(trunk)/webapp/hibernate.cfg.xml");
    static SessionFactory factory;

    public SessionFactory getSessionFactory() {
        assertTrue(hibernateConfig.exists());

        Configuration configuration = new Configuration();
        assertNotNull(configuration);

        configuration.configure(hibernateConfig);

        ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
        ServiceRegistry serviceRegistry;

        builder = builder.applySettings(configuration.getProperties());
        assertNotNull(builder);

        serviceRegistry = builder.buildServiceRegistry();
        assertNotNull(serviceRegistry);

        factory = configuration.buildSessionFactory(serviceRegistry);
        assertNotNull(factory);

        factory.getStatistics().setStatisticsEnabled(true);
        assertTrue(factory.getStatistics().isStatisticsEnabled());

        return factory;
    }
}
