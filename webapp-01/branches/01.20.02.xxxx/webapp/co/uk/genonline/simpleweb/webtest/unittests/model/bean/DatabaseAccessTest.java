package unittests.model.bean;

import co.uk.genonline.simpleweb.model.bean.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import support.TestSupportLogger;
import unittests.support.ScreensUnitTestData;
import unittests.support.TestSupport;
import unittests.support.TestSupportSessionFactory;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.List;

@RunWith(value=BlockJUnit4ClassRunner.class)
public class DatabaseAccessTest {
    private SessionFactory factory;
    ScreensManager manager;

    @Before
    public void setUp() throws Exception {
        TestSupportLogger.initLogger();
        this.factory = TestSupportSessionFactory.getSessionFactory();
    }

    @After
    public void tearDown() throws Exception {
        TestSupport.clearDatabase(factory);
    }

    @Test
    public void testGetCurrentSession() throws Exception {
        ScreensEntityBuilder screenBuilder = new ScreensEntityBuilder();
        screenBuilder.name("testDB-01")
            .screenTitleShort("test DB")
            .screenType("Mistress");
        ScreensEntity screen = screenBuilder.build();

        Session session = factory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.save(screen);
        tx.commit();

        screen.setName("testDB-02");
        session = factory.getCurrentSession();
        tx = session.beginTransaction();
        session.save(screen);
        tx.commit();

    }


}

