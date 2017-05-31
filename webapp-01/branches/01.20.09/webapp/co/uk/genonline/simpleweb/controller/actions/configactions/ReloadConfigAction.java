package co.uk.genonline.simpleweb.controller.actions.configactions;

import co.uk.genonline.simpleweb.configuration.configitems.LoggingLevel;
import co.uk.genonline.simpleweb.controller.actions.Action;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.web.gallery.*;
import org.apache.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 31/10/2013
 * Time: 18:16
 * To change this template use File | Settings | File Templates.
 */
public class ReloadConfigAction extends Action {
    public ReloadConfigAction(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    /**
     * Reload configuration items after they have been changed.
     *
     * Also need to carry out an required actions to propogate the new value (e.g. if logging level changes, need
     * to change in root logger).
     *
     * As part of this, we need to recreate the GalleryManager object from the reloaded
     * configuration and then overwrite the GalleryManager which is stored in the context.
     *
     * @return
     */
    @Override
    public RequestResult perform() {
        configuration.loadConfiguration();

        Level oldLevel = logger.getRootLevel();
        Level newLevel = ((LoggingLevel)configuration.getConfigurationItem("loggingLevel")).get();
        logger.info("Changing root logging level from <%s> to <%s>", oldLevel.toString(), newLevel.toString());
        logger.setRootLevel(newLevel);
        logger.info("logging level is now <%s>", logger.getRootLevel().toString());

        GalleryManagerConfiguration galleryManagerConfiguration =
                new GalleryManagerConfigurationDefault(configuration);

        ThumbnailManager thumbnailManager = new ThumbnailManagerDefault(galleryManagerConfiguration);

        logger.info("Creating and saving Gallery Manager in context attribute");

        GalleryManager galleryManager = new GalleryManagerDefault(
                galleryManagerConfiguration,
                thumbnailManager,
                new GalleryCarouselHtmlGenerator(galleryManagerConfiguration));

        // Overwrite existing GalleryManager with new one generated from reloaded configuration.
        request.getServletContext().setAttribute("Galleries",galleryManager);

        return new RequestResult(request, "/editConfigIndex", true);
    }
}
