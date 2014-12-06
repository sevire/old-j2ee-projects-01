package uk.co.genonline.ldav03.controller.links;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import uk.co.genonline.ldav03.model.links.LinksManager;
import uk.co.genonline.ldav03.web.TopLinks;

import java.io.FileNotFoundException;

import static uk.co.genonline.ldav03.controller.UrlMapping.*;

/**
 * Created by thomassecondary on 20/07/2014.
 */

@Controller
@RequestMapping(LINKS_CLASS_URL_MAPPING)
public class LinksController {
    Logger logger = Logger.getLogger("");

    @Autowired
    LinksManager linksManager;

    @Autowired
    TopLinks topLinks;

    @RequestMapping(method=RequestMethod.GET)
    public ModelAndView defaultRequest(ModelAndView modelAndView) throws FileNotFoundException {

        return linksViewRequest(LINKS_HOME_PAGE_VALUE, modelAndView);
    }

    @RequestMapping(value=VIEW_URL_MAPPING, method=RequestMethod.GET)
    public ModelAndView defaultViewRequest(ModelAndView modelAndView) throws FileNotFoundException {

        return defaultRequest(modelAndView);
    }

    @RequestMapping(value=VIEW_URL_MAPPING + "/" + LINKS_HOME_PAGE_URL, method=RequestMethod.GET)
    public ModelAndView homeViewRequest(ModelAndView modelAndView) throws FileNotFoundException {

        return defaultViewRequest(modelAndView);
    }

    @RequestMapping(value=VIEW_URL_MAPPING + "/{category}", method=RequestMethod.GET)
    public ModelAndView linksViewRequest(@PathVariable String category, ModelAndView modelAndView) throws FileNotFoundException {
        String linksLinkbar;

        logger.trace(String.format("Parsing links request for category: [%s]", category));

        linksLinkbar = linksManager.getLinksLinkbarByCategory(category);
        if (linksLinkbar == null) {
            throw new FileNotFoundException(String.format("Links for category [%s] not found", category));
        } else {
            modelAndView.getModel().put("linksLinkbar", linksLinkbar);
            modelAndView.setViewName("links-01-displaytype");
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
        return String.format("LinksController: <LinksManager-%s>", linksManager);
    }
}
