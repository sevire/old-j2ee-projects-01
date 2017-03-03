package co.uk.genonline.simpleweb.controller.actions.configactions;

import co.uk.genonline.simpleweb.controller.actions.DatabaseAction;
import co.uk.genonline.simpleweb.model.bean.ConfigItemBeanManager;
import co.uk.genonline.simpleweb.model.bean.ConfigurationEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 26/10/2013
 * Time: 16:07
 * To change this template use File | Settings | File Templates.
 */
public abstract class ConfigurationAction extends DatabaseAction {
    final ConfigItemBeanManager configItemBeanManager;
    final ConfigurationEntity configItems = new ConfigurationEntity();

    ConfigurationAction(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
        this.configItemBeanManager = new ConfigItemBeanManager(configItems, factory);

    }
}
