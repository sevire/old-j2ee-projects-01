package uk.co.genonline.ldav03.model.Testimonialxxx;

import org.markdown4j.Markdown4jProcessor;
import uk.co.genonline.ldav03.controller.UrlMapping;
import uk.co.genonline.ldav03.model.PageData;
import uk.co.genonline.ldav03.model.entities.TestimonialEntity;
import uk.co.genonline.ldav03.web.LinkData;

import java.io.IOException;

/**
 * Wrapper for TestimonialEntity.  Purpose is to allow additional processing to be implemented without having to
 * modify TestimonialEntity - as this is an auto-generated file and I don't want to risk its being overwritten later.
 *
 * Extra processing is:
 * - Some intelligence around name, shortName, longName so that they only need to be populated if different.
 *   Specifically: If longName isn't populated, use shortName.  If shortName isn't populated, use name.
 *
 * - Add method getTestimonialContentDecoded to parse using Markdown.
 *
 */
public class Testimonial implements PageData {
    TestimonialEntity testimonialEntity;
    LinkData linkData;

    private Testimonial() {
        // Dummy default constructor to stop instantiating without setting entity.
        //ToDo: Check whether this is the right way to do this (I think I am making this commutable)
    }

    public Testimonial(TestimonialEntity testimonialEntity) {
        this.testimonialEntity = testimonialEntity;
        linkData = new LinkData(testimonialEntity.getTestimonialName(), testimonialEntity.getTestimonialShortName(), testimonialEntity.getTestimonialLongName(), "/" + UrlMapping.TESTIMONIAL_CLASS_URL_MAPPING);
    }

    public LinkData getLinkData() {
        return linkData;
    }

    public String getTestimonialName() {
        return testimonialEntity.getTestimonialName();
    }

    public String getTestimonialSlaveName() {
        return testimonialEntity.getTestimonialSlaveName();
    }

    public int getTestimonialSortKey() {
        return testimonialEntity.getTestimonialSortKey();
    }

    public String getTestimonialContent() {
        return testimonialEntity.getTestimonialContent();
    }

    public String getTestimonialContentDecoded() {
        String outputHTML;
        Markdown4jProcessor processor = new Markdown4jProcessor();
        try {
            outputHTML = processor.process(getTestimonialContent());
        } catch (IOException e) {
            outputHTML = "Couldn't parse Markdown text";
            e.printStackTrace();
        }
        return outputHTML;
    }
}
