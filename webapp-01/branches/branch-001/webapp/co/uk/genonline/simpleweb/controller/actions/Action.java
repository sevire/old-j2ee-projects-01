package co.uk.genonline.simpleweb.controller.actions;

import co.uk.genonline.simpleweb.controller.WebLogger;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 07/08/2012
 * Time: 08:02
 * To change this template use File | Settings | File Templates.
 */
public abstract class Action {

    final protected WebLogger logger = new WebLogger();
    protected Action() {
        logger.info("Instantiating Action sub-class <%s>", this.getClass().getName());
    }
    public abstract RequestResult perform();

}
