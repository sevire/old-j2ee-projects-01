package uk.co.genonline.ldav03.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.co.genonline.ldav03.model.MistressEntity;
import uk.co.genonline.ldav03.model.MistressManager;

/**
 * Created by thomassecondary on 15/09/2014.
 */

@Controller
public class MistressUpdateController {
    Logger logger = Logger.getLogger("");

    @Autowired
    MistressManager mistressManager;

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
}
