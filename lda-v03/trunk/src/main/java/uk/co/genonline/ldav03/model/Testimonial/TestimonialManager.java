package uk.co.genonline.ldav03.model.Testimonial;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by thomassecondary on 27/10/2014.
 */
public class TestimonialManager {
    Logger logger = Logger.getLogger("");

    @Autowired
    SessionFactory sessionFactory;

    public Testimonial getTestimonialData(String testimonialName) {
        Testimonial testimonial;
        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        TestimonialEntity entityData = (TestimonialEntity) session.get(TestimonialEntity.class, testimonialName);
        if (entityData == null) {
            testimonial = null;
        } else {
            testimonial = new Testimonial(entityData);
        }
        session.close();
        return testimonial;
    }

    public void addTestimonialData(TestimonialEntity testimonialEntity) {
        logger.info("STUB: Add data for new Testimonial");
        Testimonial testimonial = new Testimonial(testimonialEntity);
        logger.info(String.format("Testimonial Data = <%s>", testimonial));

        Session session;

        logger.log(Level.INFO, String.format("sessionFactory is <%s>", sessionFactory));
        session = sessionFactory.openSession();

        session.saveOrUpdate(testimonialEntity);
        session.flush();
        session.close();
    }
}
