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
import uk.co.genonline.ldav03.model.Chamber.ChamberInformation;
import uk.co.genonline.ldav03.model.Chamber.ChamberInformationManager;
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
        Gallery gallery;

        logger.log(Level.INFO, String.format("Parsing chamber request for {%s}", chamberInformationName));

        chamberInformation = chamberInformationManager.getChamberInformationData(chamberInformationName);
        if (chamberInformation == null) {
            throw new FileNotFoundException(String.format("Chamber %s not found",chamberInformationName));
        } else {
            if (chamberInformation.getGalleryFlag() != 0) {
                gallery = new Gallery(chamberInformationName);
                modelAndView.getModel().put("gallery", gallery);
            } else {
                modelAndView.getModel().put("chamberInformationData", chamberInformation);
            }
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
