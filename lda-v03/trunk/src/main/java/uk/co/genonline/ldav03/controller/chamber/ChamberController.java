package uk.co.genonline.ldav03.controller.chamber;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.genonline.ldav03.model.Galleryxxx.Gallery;
import uk.co.genonline.ldav03.model.chamber.ChamberInformation;
import uk.co.genonline.ldav03.model.chamber.ChamberInformationManager;
import uk.co.genonline.ldav03.web.TopLinks;

import java.io.FileNotFoundException;

import static uk.co.genonline.ldav03.controller.UrlMapping.*;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@Controller
@RequestMapping(CHAMBER_CLASS_URL_MAPPING)
public class ChamberController {
    Logger logger = Logger.getLogger("");

    @Autowired
    ChamberInformationManager chamberInformationManager;

    @Autowired
    TopLinks topLinks;

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView defaultRequest(ModelAndView modelAndView) throws FileNotFoundException {

        return chamberViewRequest(CHAMBERS_HOME_PAGE_VALUE, modelAndView);
    }

    @RequestMapping(value=VIEW_URL_MAPPING, method=RequestMethod.GET)
    public ModelAndView defaultViewRequest(ModelAndView modelAndView) throws FileNotFoundException {

        return defaultRequest(modelAndView);
    }

    @RequestMapping(value=VIEW_URL_MAPPING + "/" + CHAMBERS_HOME_PAGE_URL, method=RequestMethod.GET)
    public ModelAndView homeViewRequest(ModelAndView modelAndView) throws FileNotFoundException {

        return defaultViewRequest(modelAndView);
    }

    @RequestMapping(value=VIEW_URL_MAPPING + "/{chamberInformationName}", method=RequestMethod.GET)
    public ModelAndView chamberViewRequest(@PathVariable String chamberInformationName, ModelAndView modelAndView) throws FileNotFoundException {
        ChamberInformation chamberInformation;

        logger.log(Level.TRACE, String.format("Parsing chamber request for {%s}", chamberInformationName));

        chamberInformation = chamberInformationManager.getChamberInformationData(chamberInformationName);
        if (chamberInformation == null) {
            throw new FileNotFoundException(String.format("Chamber %s not found",chamberInformationName));
        } else {
            modelAndView.getModel().put("data", chamberInformation);
            if (chamberInformation.isGalleryFlag()) {
                String galleryHtml = new Gallery(chamberInformationName).getHtml();
                modelAndView.getModel().put("gallery", galleryHtml);
            }
            String chamberLinkBar = chamberInformationManager.getChamberLinkbarHtml(chamberInformationName);
            modelAndView.getModel().put("siblingNavbar", chamberLinkBar);
            modelAndView.setViewName("chamber-01-displaytype");
            modelAndView.getModel().put("topLinkbar", topLinks.getTopLinkbar());
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
        return String.format("ChamberRequestController: <ChamberInformationManager-%s>", chamberInformationManager);
    }
}
