package uk.co.genonline.ldav03.model.User;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import uk.co.genonline.ldav03.model.EntityManager;
import uk.co.genonline.ldav03.model.entities.UserEntity;

import java.io.Serializable;

/**
 * Created by thomassecondary on 17/09/2014.
 */
public class UserManager implements EntityManager {
    Logger logger = Logger.getLogger("");

    @Autowired
    SessionFactory sessionFactory;

    User user;

    @Override
    public void add(Serializable entity) {

    }

    @Override
    public void update(Serializable entity) {

    }

    @Override
    public Serializable get(Object key) {
        User user;
        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        UserEntity entityData = (UserEntity) session.get(UserEntity.class, (String) key);
        if (entityData == null) {
            user = null;
        } else {
            user = new User(entityData);
        }
        session.close();
        return entityData;
    }


    @Override
    public Serializable[] getAll() {
        return new Serializable[0];
    }

    @Override
    public void delete(Object key) {

    }
}
