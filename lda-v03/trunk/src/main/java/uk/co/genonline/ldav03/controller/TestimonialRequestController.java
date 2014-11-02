package uk.co.genonline.ldav03.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.genonline.ldav03.model.Testimonial.Testimonial;
import uk.co.genonline.ldav03.model.Testimonial.TestimonialManager;

import java.io.FileNotFoundException;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@Controller
@RequestMapping("/testimonialView")
public class TestimonialRequestController {
    Logger logger = Logger.getLogger("");

    @Autowired
    TestimonialManager testimonialManager;

    @RequestMapping(value="/{testimonialName}", method=RequestMethod.GET)
    public ModelAndView testimonialRequest(@PathVariable String testimonialName, ModelAndView modelAndView) throws FileNotFoundException {
        Testimonial testimonial;

        logger.log(Level.INFO, String.format("Parsing testimonial request for {%s}", testimonialName));

        testimonial = testimonialManager.getTestimonialData(testimonialName);
        if (testimonial == null) {
            throw new FileNotFoundException(String.format("Testimonial %s not found", testimonialName));
        } else {
            modelAndView.getModel().put("testimonialData", testimonial);
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
        return String.format("TestimonialRequestController: <TestimonialManager-%s>", testimonialManager);
    }
}
