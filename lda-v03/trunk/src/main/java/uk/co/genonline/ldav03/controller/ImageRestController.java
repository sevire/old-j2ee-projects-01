package uk.co.genonline.ldav03.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.util.UriTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;

/**
 * Created by thomassecondary on 31/08/2014.
 *
 * Controller to serve REST style image requests.
 *
 */

@RestController
public class ImageRestController {
    static final String IMAGE_URL_BASE = "/galleryimage";
    static final String BASE_PATH = "/Users/thomassecondary/Documents/Princess/Website/webgalleries";

    Logger logger = Logger.getLogger("");

    ImageRestController() {
        logger.setLevel(Level.ALL);
    }


    @RequestMapping(value=IMAGE_URL_BASE + "/**", method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getFile(HttpServletRequest request) throws FileNotFoundException {
        String path;
        String fullPath;

        logger.info(String.format("Processing galleryimage request, parsing."));

        String URL = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        logger.debug(String.format("URL to parse is <%s>", URL));

        UriTemplate template = new UriTemplate(IMAGE_URL_BASE + File.separator + "{path}");
        boolean isTemplateMatched = template.matches(URL);

        if(isTemplateMatched) {
            Map<String, String> matchTemplate;
            matchTemplate = template.match(URL);
            path = matchTemplate.get("path");
        } else {
            throw new FileNotFoundException("Path to image not recognised");
        }
        FileSystemResource resource = new FileSystemResource(new File(BASE_PATH, path));
        logger.debug(String.format("resource = <%s>", resource));
        if (!resource.exists()) {
            throw new FileNotFoundException("File not found for " + resource.getPath());
        } else {
            logger.debug(String.format("Full path <%s>", resource.getPath()));
        }
        ResponseEntity<FileSystemResource> responseEntity = new ResponseEntity<FileSystemResource>(resource, HttpStatus.OK);
        return responseEntity;
    }

    @ResponseStatus(value=HttpStatus.NOT_FOUND, reason="Image resource not found")
    @ExceptionHandler(value=FileNotFoundException.class)
    public void handleFileNotFound() {
/*
        logger.warn(String.format("FileNotFound exception thrown: <%s>", ex.getMessage()));
        ResponseEntity<FileSystemResource> responseEntity = new ResponseEntity<FileSystemResource>(HttpStatus.NOT_FOUND);
        return responseEntity;
*/
    }


}
