package co.uk.genonline.simpleweb.controller;

import javax.servlet.http.HttpServlet;

/**
 * Controller to serve images from a folder outside the folder structure of the web app.
 *
 * The purpose of this controller is to allow images which are content images (e.g. gallery images) to be located
 * outside the deployed web app, which will allow the war file to be much smaller and make deployment easier.  It will
 * also make the management of content easier as it doesn't need to be replicated within the project structure.
 *
 * The approach is to route
 */
public class ImageServerController extends HttpServlet {
    private final WebLogger logger = new WebLogger();
}
