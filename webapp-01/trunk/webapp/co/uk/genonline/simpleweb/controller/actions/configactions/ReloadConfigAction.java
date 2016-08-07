package co.uk.genonline.simpleweb.controller.actions.configactions;

import co.uk.genonline.simpleweb.controller.actions.Action;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;

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
     * @return
     */
    @Override
    public RequestResult perform() {
        configuration.loadConfiguration();

        return new RequestResult(request, "/editConfigIndex", true);
    }
}
