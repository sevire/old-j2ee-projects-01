package uk.co.genonline.ldav03.model.Testimonial;

import org.markdown4j.Markdown4jProcessor;

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
public class Testimonial {
    TestimonialEntity testimonialEntity;

    public Testimonial(TestimonialEntity testimonialEntity) {
        this.testimonialEntity = testimonialEntity;
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

    public String getTestimonialLongName() {
        if (testimonialEntity.getTestimonialLongName() == null || testimonialEntity.getTestimonialLongName().isEmpty()) {
            return getTestimonialShortName();
        } else {
            return testimonialEntity.getTestimonialLongName();
        }
    }

    public String getTestimonialShortName() {
        if (testimonialEntity.getTestimonialShortName() == null || testimonialEntity.getTestimonialShortName().isEmpty()) {
            return getTestimonialName();
        } else {
            return testimonialEntity.getTestimonialShortName();
        }
    }
}
