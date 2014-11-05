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
import uk.co.genonline.ldav03.model.chamber.ChamberInformation;
import uk.co.genonline.ldav03.model.chamber.ChamberInformationManager;
import uk.co.genonline.ldav03.model.Gallery.Gallery;

import java.io.FileNotFoundException;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@Controller
@RequestMapping("/chamberView")
public class ChamberRequestController {
    Logger logger = Logger.getLogger("");

    @Autowired
    ChamberInformationManager chamberInformationManager;

    @RequestMapping(value="/{chamberInformationName}", method=RequestMethod.GET)
    public ModelAndView chamberRequest(@PathVariable String chamberInformationName, ModelAndView modelAndView) throws FileNotFoundException {
        ChamberInformation chamberInformation;

        logger.log(Level.INFO, String.format("Parsing chamber request for {%s}", chamberInformationName));

        chamberInformation = chamberInformationManager.getChamberInformationData(chamberInformationName);
        if (chamberInformation == null) {
            throw new FileNotFoundException(String.format("chamber %s not found",chamberInformationName));
        } else {
            if (chamberInformation.isGalleryFlag()) {
                String galleryHtml = new Gallery(chamberInformationName).getHtml();
                modelAndView.getModel().put("gallery", galleryHtml);
            }
            modelAndView.getModel().put("chamberInformationData", chamberInformation);
            modelAndView.setViewName("chamber-01-displaytype");
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
