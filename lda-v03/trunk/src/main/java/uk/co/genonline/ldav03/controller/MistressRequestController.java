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
import uk.co.genonline.ldav03.model.Mistress;
import uk.co.genonline.ldav03.model.MistressManager;

import java.io.FileNotFoundException;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@Controller
@RequestMapping("/")
public class MistressRequestController {
    Logger logger = Logger.getLogger("");

    @Autowired
    MistressManager mistressManager;

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView defaultRequest(ModelAndView modelAndView) throws FileNotFoundException {
        return mistressRequest("lucina", modelAndView);
    }

    @RequestMapping(value="/mistressview/{mistressName}", method=RequestMethod.GET)
    public ModelAndView mistressRequest(@PathVariable String mistressName, ModelAndView modelAndView) throws FileNotFoundException {
        Mistress mistressData;

        logger.log(Level.INFO, String.format("Parsing mistress request for {%s}",mistressName));

        mistressData = mistressManager.getMistressData(mistressName);
        if (mistressData == null) {
            throw new FileNotFoundException(String.format("Mistress %s not found",mistressName));
        } else {
            modelAndView.setViewName("mistress-01-displaytype");
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
        return String.format("MistressRequestController: <MistressManager-%s>", mistressManager);
    }
}
