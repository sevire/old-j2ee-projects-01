package uk.co.genonline.ldav03.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.genonline.ldav03.model.Mistress.Mistress;
import uk.co.genonline.ldav03.model.Mistress.MistressEntity;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/dispatcher-servlet.xml", "file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class MistressTest {
    @Autowired
    SessionFactory sessionFactory;
    Session session;
    Mistress data;

    @Before
    public void setup() {
        session = sessionFactory.openSession();
    }

    @Test
    public void readTest() {
        data = new Mistress((MistressEntity) session.get(MistressEntity.class, "lucina"));
        Assert.assertEquals("Long name incorrect", "Princess Lucina", data.getMistressLongName());
        Assert.assertEquals("Short name incorrect", "Princess Lucina", data.getMistressShortName());
    }

}
