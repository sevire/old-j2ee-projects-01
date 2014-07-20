package uk.co.genonline.springapp05.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by thomassecondary on 20/07/2014.
 */
public class DbAccessTrial {
    SessionFactory sessionFactory;

    public DbAccessTrial(SessionFactory factory) {
        this.sessionFactory = factory;
    }

    public MistressEntity getMistressData(String mistressName) {
        Session session = sessionFactory.openSession();

        MistressEntity data;
        data = (MistressEntity) session.get(MistressEntity.class, mistressName);

        return data;
    }
}
