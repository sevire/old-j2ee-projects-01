package uk.co.genonline.model;

import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uk.co.genonline.springapp05.model.DbAccessTrial;
import uk.co.genonline.springapp05.model.MistressEntity;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/resources/springconfig.xml")
public class DbAccessTest {
    @Autowired
    SessionFactory sessionFactory;

    @Before
    public void setup() {

    }

    @Test
    public void readTest() {
        DbAccessTrial access = new DbAccessTrial(sessionFactory);
        MistressEntity data;
        data = access.getMistressData("lucina");
        Assert.assertEquals("Wrong data", "Princess Lucina", data.getMistressLongName());
    }

}
