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
    static File hibernateConfig = new File("/Users/thomassecondary/Projects/lda-webapp/webapp/co/uk/genonline/simpleweb/webtest/hibernate.cfg.xml");
    static SessionFactory factory;

    public static SessionFactory getSessionFactory() {
        if (factory == null) {
            assertTrue(hibernateConfig.exists());

            Configuration configuration = new Configuration();
            assertNotNull(configuration);

            configuration.configure(hibernateConfig);

            ServiceRegistryBuilder builder = new ServiceRegistryBuilder();
            builder.applySettings(configuration.getProperties());
            ServiceRegistry serviceRegistry = builder.buildServiceRegistry();

            assertNotNull(builder);

            assertNotNull(serviceRegistry);

            factory = configuration.buildSessionFactory(serviceRegistry);
            assertNotNull(factory);
        }

        return factory;
    }
}
