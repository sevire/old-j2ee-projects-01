package uk.co.genonline.ldav03.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.io.FileNotFoundException;

/**
 * Controller to handle empty request (i.e. domain but no url)
 *
 * Re-direct to index.jsp
 */

@Controller
public class DefaultController {
    Logger logger = Logger.getLogger("");

    @RequestMapping(value="/", method=RequestMethod.GET)
    public ModelAndView defaultRequest(ModelAndView modelAndView) throws FileNotFoundException {
        logger.trace(String.format("Empty request, redirecting to index page"));
        RedirectView redirectView = new RedirectView("/index");
        redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        modelAndView.setView(redirectView);
        return modelAndView;
    }

    @RequestMapping(value="/index", method=RequestMethod.GET)
    public ModelAndView indexRequest(ModelAndView modelAndView) throws FileNotFoundException {
        logger.trace("Processing request for index");
        modelAndView.setViewName("index");

        // ToDo: Check whether this is best practice - passed in MAV and then return same object but modified
        return modelAndView;
    }

/*    @RequestMapping(value="*//**", method=RequestMethod.GET)
    public ModelAndView unMappedRequest(HttpServletRequest request, ModelAndView modelAndView)  {
        modelAndView.setViewName("pagenotfound");
        String requestUrl = request.getRequestURI().toString();
        logger.trace(String.format("Unrecognised request, page not found for <%s>", requestUrl));
        modelAndView.getModelMap().addAttribute("message", "Page %s not found");
        return modelAndView;
    }*/
}
