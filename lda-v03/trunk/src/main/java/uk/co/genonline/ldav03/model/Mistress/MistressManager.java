package uk.co.genonline.ldav03.model.Mistress;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 *
 */
public class MistressManager {
    Logger logger = Logger.getLogger("");

    @Autowired
    SessionFactory sessionFactory;

    public Mistress getMistressData(String mistressName) {
        Mistress mistressData;
        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        MistressEntity entityData = (MistressEntity) session.get(MistressEntity.class, mistressName);
        if (entityData == null) {
            mistressData = null;
        } else {
            mistressData = new Mistress(entityData);
        }
        session.close();
        return mistressData;
    }

    public void addMistressData(MistressEntity mistressEntity) {
        logger.info("STUB: Add data for new Mistress");
        Mistress mistress = new Mistress(mistressEntity);
        logger.info(String.format("Mistress Data = <%s>", mistress));

        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        session.saveOrUpdate(mistressEntity);
        session.flush();
        session.close();

    }
}
