package uk.co.genonline.springapp05.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.genonline.springapp05.model.DbAccessTrial;
import uk.co.genonline.springapp05.model.MistressEntity;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@Controller
@RequestMapping("/")
public class RequestController {
    Logger logger = Logger.getLogger("");
    @Autowired
    SessionFactory sessionFactory;

    @RequestMapping(value="mistress/{mistressName}", method=RequestMethod.GET)
    public String mistressRequest(@PathVariable String mistressName, ModelMap model) {
        logger.log(Level.INFO, String.format("Parsing mistress request for {%s}",mistressName));
        DbAccessTrial data = new DbAccessTrial(sessionFactory);
        MistressEntity mistress = data.getMistressData(mistressName);

        model.addAttribute("data", mistress.getMistressLongName());
        return "mistress";
    }
}
