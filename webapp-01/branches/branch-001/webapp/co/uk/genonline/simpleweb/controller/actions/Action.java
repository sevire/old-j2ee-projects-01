package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.controller.WebLogger;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:02
 *
 * Supertype used within Controller (ControllerHelper) to process incoming requests. Each request will need
 * a sub-type of this class.  The appropriate sub-type of Action is selected by ActionFactory on the
 * incoming request.
 *
 * The `perform()` method
 * is the abstr
 */
public abstract class Action {

    final protected WebLogger logger = new WebLogger();
    protected Action() {
        logger.info("Instantiating Action sub-class <%s>", this.getClass().getName());
    }
    public abstract RequestResult perform();

}
