package uk.co.genonline.ldav03.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.genonline.ldav03.model.Gallery.Gallery;
import uk.co.genonline.ldav03.model.Mistress.Mistress;
import uk.co.genonline.ldav03.model.Mistress.MistressEntity;
import uk.co.genonline.ldav03.model.Mistress.MistressManager;

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

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView defaultRequest(ModelAndView modelAndView) throws FileNotFoundException {
        return mistressRequest("lucina", modelAndView);
    }

    @RequestMapping(value=MISTRESS_VIEW_URL_MAPPING + "/{mistressName}", method=RequestMethod.GET)
    public ModelAndView mistressRequest(@PathVariable String mistressName, ModelAndView modelAndView) throws FileNotFoundException {
        Mistress mistressData;

        logger.log(Level.INFO, String.format("Parsing mistress request for {%s}",mistressName));

        mistressData = mistressManager.getMistressData(mistressName);
        if (mistressData == null) {
            throw new FileNotFoundException(String.format("Mistress %s not found",mistressName));
        } else {
            modelAndView.getModel().put("mistressData", mistressData);
            if (mistressData.isGalleryFlag()) {
                String galleryHtml = new Gallery(mistressName).getHtml();
                modelAndView.getModel().put("gallery", galleryHtml == null ? "" : galleryHtml);
            }
            String mistressLinkBar = mistressManager.getMistressLinkbarHtml();
            modelAndView.getModel().put("mistressNavbar", mistressLinkBar);
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

    @RequestMapping(value="/mistressadd", method=RequestMethod.GET)
    public String mistressAddForm(Model model) {
        MistressEntity mistressEntity = mistressManager.getMistressData("lucina").getMistressEntity();
        logger.debug(String.format("mistressEntity:mistressName = <%s>", mistressEntity.getMistressName()));

        model.addAttribute("mistressEntity", mistressEntity);
        return "mistress-update-01-displaytype";
    }

    @RequestMapping(value="/mistressadd", method=RequestMethod.POST)
    public String mistressProcessUpdate(@ModelAttribute MistressEntity mistressEntity, Model model) {
        mistressManager.addMistressData(mistressEntity);
        model.addAttribute("mistressEntity", mistressEntity);
        return "mistress-01-displaytype";
    }


    public String toString() {
        return String.format("MistressRequestController: <MistressManager-%s>", mistressManager);
    }
}
