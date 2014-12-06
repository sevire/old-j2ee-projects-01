package uk.co.genonline.ldav03.controller.mistress;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.genonline.ldav03.model.Galleryxxx.Gallery;
import uk.co.genonline.ldav03.model.Mistressxxx.Mistress;
import uk.co.genonline.ldav03.model.Mistressxxx.MistressManager;
import uk.co.genonline.ldav03.model.entities.MistressEntity;
import uk.co.genonline.ldav03.web.TopLinks;

import java.io.FileNotFoundException;

import static uk.co.genonline.ldav03.controller.UrlMapping.*;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@Controller
@RequestMapping(MISTRESS_CLASS_URL_MAPPING)
public class MistressController {
    Logger logger = Logger.getLogger("");

    @Autowired
    MistressManager mistressManager;

    @Autowired
    TopLinks topLinks;

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView defaultRequest(ModelAndView modelAndView) throws FileNotFoundException {
        // ToDo: Need non hard-coded way of defining default pages - flag in database or specific record in configuration
        return mistressViewRequest(MISTRESS_HOME_PAGE_VALUE, modelAndView);
    }

    @RequestMapping(value=VIEW_URL_MAPPING, method=RequestMethod.GET)
    public ModelAndView defaultViewRequest(ModelAndView modelAndView) throws FileNotFoundException {
        return mistressViewRequest(MISTRESS_HOME_PAGE_VALUE, modelAndView);
    }

    @RequestMapping(value=VIEW_URL_MAPPING + "/" + MISTRESS_HOME_PAGE_URL, method=RequestMethod.GET)
    public ModelAndView homeViewRequest(ModelAndView modelAndView) throws FileNotFoundException {
        return defaultViewRequest(modelAndView);
    }

    @RequestMapping(value=VIEW_URL_MAPPING + "/{mistressName}", method=RequestMethod.GET)
    public ModelAndView mistressViewRequest(@PathVariable String mistressName, ModelAndView modelAndView) throws FileNotFoundException {
        Mistress mistressData;

        logger.log(Level.INFO, String.format("Parsing mistress request for {%s}",mistressName));

        mistressData = mistressManager.getMistressRecordByNameWithTestimonials(mistressName);
        if (mistressData == null) {
            throw new FileNotFoundException(String.format("Mistress %s not found", mistressName));
        } else {
            modelAndView.getModel().put("data", mistressData);
            if (mistressData.isGalleryFlag()) {
                String galleryHtml = new Gallery(mistressName).getHtml();
                modelAndView.getModel().put("gallery", galleryHtml == null ? "" : galleryHtml);
            }
            String mistressLinkBar = mistressManager.getMistressLinkbarHtml(mistressName);
            String associatedTestimonialsLinkBar = mistressManager.getTestimonialLinkbarHtml();
            modelAndView.getModel().put("siblingNavbar", mistressLinkBar);
            modelAndView.getModel().put("testimonialLinkbar", associatedTestimonialsLinkBar);
            modelAndView.getModel().put("topLinkbar", topLinks.getTopLinkbar());
            modelAndView.setViewName("mistress-02-displaytype");

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

    @RequestMapping(value=ADD_URL_MAPPING, method=RequestMethod.GET)
    public String mistressAddForm(Model model) {
        MistressEntity mistressEntity = mistressManager.getMistressRecordByName("lucina").getMistressEntity();
        logger.debug(String.format("mistressEntity:mistressName = <%s>", mistressEntity.getMistressName()));

        model.addAttribute("mistressEntity", mistressEntity);
        return "mistress-update-01-displaytype";
    }

    @RequestMapping(value=ADD_URL_MAPPING, method=RequestMethod.POST)
    public String mistressProcessUpdate(@ModelAttribute MistressEntity mistressEntity, Model model) {
        mistressManager.addMistressData(mistressEntity);
        model.addAttribute("mistressEntity", mistressEntity);
        return "mistress-01-displaytype";
    }


    public String toString() {
        return String.format("MistressRequestController: <MistressManager-%s>", mistressManager);
    }
}
