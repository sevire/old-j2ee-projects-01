package uk.co.genonline.springapp05.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.genonline.springapp05.model.Mistress;
import uk.co.genonline.springapp05.model.MistressManager;

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
    public String defaultRequest(ModelMap model) {
        return mistressRequest("lucina", model);
    }

    @RequestMapping(value="mistress/{mistressName}", method=RequestMethod.GET)
    public String mistressRequest(@PathVariable String mistressName, ModelMap model) {
        logger.log(Level.INFO, String.format("Parsing mistress request for {%s}",mistressName));
        Mistress mistressData = mistressManager.getMistressData(mistressName);
        model.addAttribute("mistressData", mistressData);
        return "mistress";
    }
}
