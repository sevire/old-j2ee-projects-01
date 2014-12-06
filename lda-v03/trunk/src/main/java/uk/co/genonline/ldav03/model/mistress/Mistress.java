package uk.co.genonline.ldav03.model.mistress;

import org.markdown4j.Markdown4jProcessor;
import uk.co.genonline.ldav03.controller.UrlMapping;
import uk.co.genonline.ldav03.model.PageData;
import uk.co.genonline.ldav03.model.testimonial.Testimonial;
import uk.co.genonline.ldav03.model.entities.MistressEntity;
import uk.co.genonline.ldav03.model.entities.TestimonialEntity;
import uk.co.genonline.ldav03.web.LinkData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

/**
 * The reason for this class is to keep auto-generated classes distinct from those which have been hand-written.
 * I am not sure whether this is strictly required - as far as I can see the generation of entity classes does not
 * overwrite any changes which have been made - however I expect that occasionally I will want to re-generate the entity
 * classes from scratch and this is a clean way of allowing that.
 *
 * I want to add some processing around access to certain fields, such as name, shortName, longName because of the rules
 * I want to implement that allows fields to be left blank if they are the same as another (e.g. if longName is blank, use
 * shortName).
 */
public class Mistress implements PageData {
    MistressEntity mistressEntity;
    LinkData linkData;

    public Mistress(MistressEntity mistressEntity) {
        this.mistressEntity = mistressEntity;
        this.linkData = new LinkData(mistressEntity.getMistressName(), mistressEntity.getMistressShortName(), mistressEntity.getMistressLongName(), UrlMapping.MISTRESS_CLASS_URL_MAPPING);
    }

    public LinkData getLinkData() {
        return linkData;
    }

    /**
     *
     * @return
     */
    public MistressEntity getMistressEntity() {
        return mistressEntity;
    }

    /**
     * Just passes through the mistress name (primary key) field
     *
     * @return Mistress Name field.
     */
    public String getMistressName() {
        return mistressEntity.getMistressName();
    }

    /**
     * Just passes through the content data with no change
     * @return Mistress Content.
     */
    public String getMistressContent() {
        return mistressEntity.getMistressContent();
    }

    public String getMistressContentDecoded() {
        String outputHTML;
        Markdown4jProcessor processor = new Markdown4jProcessor();
        try {
            outputHTML = processor.process(getMistressContent());
        } catch (IOException e) {
            outputHTML = "Couldn't parse Markdown text";
            e.printStackTrace();
        }
        return outputHTML;
    }


    public String getMistressContentType() {
        return mistressEntity.getMistressContentType();
    }

    public boolean isGalleryFlag() {
        return mistressEntity.getGalleryFlag() != 0;
    }

    public String getMistressTwitterUserName() {
        return mistressEntity.getMistressTwitterUserName();
    }

    public String getMistressWebsiteAddress() {
        return mistressEntity.getMistressWebsiteAddress();
    }

    public String getMistressEmailAddress() {
        return mistressEntity.getMistressEmailAddress();
    }

    public String getMistressTelephoneNumber() {
        return mistressEntity.getMistressTelephoneNumber();
    }

    public String toString() {
        return String.format("Mistress: <name:%s> <shortname:%s> <longname:%s>", getMistressName(), linkData.getShortName(), linkData.getLongName());
    }

    public Collection<Testimonial> getTestimonialsByMistressName() {
        Collection<Testimonial> testimonials = new ArrayList<Testimonial>();
        Collection<TestimonialEntity> testimonialEntities = mistressEntity.getTestimonialsByMistressName();

        for (TestimonialEntity entity : testimonialEntities) {
            testimonials.add(new Testimonial(entity));
        }
        return testimonials;
    }

    public void setTestimonialsByMistressName(Collection<TestimonialEntity> testimonialsByMistressName) {
        mistressEntity.setTestimonialsByMistressName(testimonialsByMistressName);
    }

}
