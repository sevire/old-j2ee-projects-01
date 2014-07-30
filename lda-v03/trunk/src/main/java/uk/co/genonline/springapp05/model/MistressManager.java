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
        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        Session session = sessionFactory.openSession();
        Mistress mistressData = new Mistress((MistressEntity) session.get(MistressEntity.class, mistressName));
        session.close();
        return mistressData;
    }
}
