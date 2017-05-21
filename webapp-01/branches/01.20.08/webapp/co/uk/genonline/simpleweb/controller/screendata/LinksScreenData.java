package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.controller.screendata.displaybeans.LinksDataBean;
import co.uk.genonline.simpleweb.model.bean.LinksConfiguration;
import co.uk.genonline.simpleweb.model.bean.LinksManager;
import co.uk.genonline.simpleweb.model.bean.LinksManagerNonCaching;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 24/10/2013
 * Time: 18:42
 *
 * ScreenData component which serves a couple of purposes:
 * 1. ScreenData component to drive new links page
 * 2. More structured ScreenData component which could be used to retrospectively update main MistressScreenData
 *    component.
 */
public class LinksScreenData extends MistressScreenData {
    public LinksScreenData(String screenType) {
        super(screenType);
        mistressScreenDataFlags = new MistressScreenDataFlags(
                true,
                true,
                true,
                true,
                true,
                true
        );
    }
}
