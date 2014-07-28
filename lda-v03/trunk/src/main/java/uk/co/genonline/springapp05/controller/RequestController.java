package uk.co.genonline.springapp05.controller;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.co.genonline.springapp05.model.DbAccessTrial;
import uk.co.genonline.springapp05.model.MistressEntity;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@Controller
@RequestMapping("/")
public class RequestController {
    @Autowired
    SessionFactory sessionFactory;

    @RequestMapping(value="mistress/{mistressName}", method=RequestMethod.GET)
    public String mistressRequest(@RequestParam String mistressName, Model model) {
        DbAccessTrial data = new DbAccessTrial(sessionFactory);
        MistressEntity mistress = data.getMistressData(mistressName);

        model.addAttribute("data", mistress.getMistressContent());
        return "WEB-INF/mistress.jsp";
    }
}
