package uk.co.genonline.ldav03.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import uk.co.genonline.ldav03.model.Mistress;
import uk.co.genonline.ldav03.model.MistressManager;

import java.io.FileNotFoundException;

/**
 * Implements the REST operations which expose the Mistress data.
 */
@RestController
@RequestMapping("/mistress")
public class MistressRestController {
    Logger logger = Logger.getLogger("");

    @Autowired
    MistressManager mistressManager;

    @RequestMapping(value="/get/{mistressName}", method=RequestMethod.GET, headers="Accept=*/*")
    public Mistress mistressRequest(@PathVariable String mistressName) throws FileNotFoundException {
        Mistress mistressData;

        logger.log(Level.INFO, String.format("Parsing mistress REST service request for {%s}",mistressName));

        mistressData = mistressManager.getMistressData(mistressName);
        if (mistressData == null) {
            throw new FileNotFoundException(String.format("Mistress %s not found",mistressName));
        } else {
            return mistressData;
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
