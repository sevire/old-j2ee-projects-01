package uk.co.genonline.ldav03.controller.testimonial;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.genonline.ldav03.model.testimonial.Testimonial;
import uk.co.genonline.ldav03.model.testimonial.TestimonialManager;
import uk.co.genonline.ldav03.web.TopLinks;

import java.io.FileNotFoundException;

import static uk.co.genonline.ldav03.controller.UrlMapping.TESTIMONIALS_HOME_PAGE_URL;
import static uk.co.genonline.ldav03.controller.UrlMapping.TESTIMONIALS_HOME_PAGE_VALUE;
import static uk.co.genonline.ldav03.controller.UrlMapping.VIEW_URL_MAPPING;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@Controller
@RequestMapping("/testimonial")
public class TestimonialController {
    Logger logger = Logger.getLogger("");

    @Autowired
    TestimonialManager testimonialManager;

    @Autowired
    TopLinks topLinks;

    @RequestMapping(value=VIEW_URL_MAPPING, method=RequestMethod.GET)
    public ModelAndView defaultViewRequest(ModelAndView modelAndView) throws FileNotFoundException {

        return testimonialRequest(TESTIMONIALS_HOME_PAGE_VALUE, modelAndView);
    }

    @RequestMapping(value=VIEW_URL_MAPPING + "/" + TESTIMONIALS_HOME_PAGE_URL, method=RequestMethod.GET)
    public ModelAndView homeViewRequest(ModelAndView modelAndView) throws FileNotFoundException {

        return defaultViewRequest(modelAndView);
    }

    @RequestMapping(value="/view/{testimonialName}", method=RequestMethod.GET)
    public ModelAndView testimonialRequest(@PathVariable String testimonialName, ModelAndView modelAndView) throws FileNotFoundException {
        Testimonial testimonial;

        logger.log(Level.INFO, String.format("Parsing testimonial request for {%s}", testimonialName));

        testimonial = testimonialManager.getTestimonialData(testimonialName);
        if (testimonial == null) {
            throw new FileNotFoundException(String.format("Testimonial %s not found", testimonialName));
        } else {
            String testimonialLinkBar = testimonialManager.getTestimonialLinkbarHtml(testimonialName);
            modelAndView.getModel().put("data", testimonial);
            modelAndView.getModel().put("topLinkbar", topLinks.getTopLinkbar());
            modelAndView.getModel().put("siblingLinkbar", testimonialLinkBar);
            modelAndView.setViewName("testimonial-01-displaytype");
            return modelAndView;
        }
    }

    @ExceptionHandler(value=FileNotFoundException.class)
    public ModelAndView handleFileNotFound(FileNotFoundException ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("pagenotfound");
        modelAndView.getModelMap().addAttribute("message", ex.getMessage());
        return modelAndView;
    }

    public String toString() {
        return String.format("TestimonialController: <TestimonialManager-%s>", testimonialManager);
    }
}
