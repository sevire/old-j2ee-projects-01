package uk.co.genonline.springapp05.model;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 */
public class MistressManager {
    Logger logger = Logger.getLogger("");

    @Autowired
    SessionFactory sessionFactory;

    Mistress mistress;

    MistressManager(Mistress mistress) {
        this.mistress = mistress;
    }

    public Mistress getMistressData(String mistressName) {
        Mistress mistressData;
        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        MistressEntity entityData = (MistressEntity) session.get(MistressEntity.class, mistressName);
        if (entityData == null) {
            mistressData = null;
        } else {
            mistressData = new Mistress((MistressEntity) session.get(MistressEntity.class, mistressName));
        }
        session.close();
        return mistressData;
    }

    public String toString() {
        return String.format("MistressManager: <SessionFactory: %s> <Mistress: %s>", sessionFactory, mistress);
    }
}
