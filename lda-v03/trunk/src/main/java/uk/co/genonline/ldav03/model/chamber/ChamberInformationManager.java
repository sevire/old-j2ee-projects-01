package uk.co.genonline.ldav03.model.chamber;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by thomassecondary on 26/10/2014.
 */
public class ChamberInformationManager {
    Logger logger = Logger.getLogger("");

    @Autowired
    SessionFactory sessionFactory;

    public ChamberInformation getChamberInformationData(String chamberInformationName) {
        ChamberInformation chamberInformationData;
        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        ChamberInformationEntity entityData = (ChamberInformationEntity) session.get(ChamberInformationEntity.class, chamberInformationName);
        if (entityData == null) {
            chamberInformationData = null;
        } else {
            chamberInformationData = new ChamberInformation(entityData);
        }
        session.close();
        return chamberInformationData;
    }

    public void addChamberInformationData(ChamberInformationEntity ChamberInformationEntity) {
        logger.info("STUB: Add data for new ChamberInformation");
        ChamberInformation ChamberInformation = new ChamberInformation(ChamberInformationEntity);
        logger.info(String.format("ChamberInformation Data = <%s>", ChamberInformation));

        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        session.saveOrUpdate(ChamberInformationEntity);
        session.flush();
        session.close();

    }

}
