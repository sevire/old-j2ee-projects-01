package uk.co.genonline.ldav03.controller;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

/**
 * Created by thomassecondary on 31/08/2014.
 */
@RestController
@RequestMapping(value="/galleryimage")
public class ImageRestController {
    Logger logger = Logger.getLogger("");

    ImageRestController() {
        logger.setLevel(Level.ALL);
    }

    public static final String BASE_PATH = "/Users/thomassecondary/Documents/Princess/Website/webgalleries";

    @RequestMapping(value = "{galleryName}/{fileName:.+}" , method = RequestMethod.GET)
    public ResponseEntity<FileSystemResource> getFile(@PathVariable String galleryName, @PathVariable String fileName) {
        logger.info(String.format("Processing request for gallery <%s>, image <%s>", galleryName, fileName));
        String fullPath = BASE_PATH + File.separator + galleryName;
        FileSystemResource resource = new FileSystemResource(new File(fullPath, fileName));
        logger.debug(String.format("Full path <%s>", resource.getPath()));

        ResponseEntity<FileSystemResource> responseEntity = new ResponseEntity<FileSystemResource>(resource, HttpStatus.OK);
        return responseEntity;
    }
}
