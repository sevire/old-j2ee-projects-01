package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.configuration.configitems.LinkDaysBetweenChecks;
import co.uk.genonline.simpleweb.configuration.general.Configuration;
import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.controller.screendata.displaybeans.LinksDataBean;
import co.uk.genonline.simpleweb.model.bean.*;
import co.uk.genonline.simpleweb.monitoring.Collator;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.hibernate.SessionFactory;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by thomassecondary on 13/03/2017.
 */
public class GeneralScreenData extends ScreenData {
    private LinksDataBean linksData = new LinksDataBean();
    private SessionFactory factory = null;

    public GeneralScreenData(String screenType) {
        super(screenType);

    }

    public RequestResult setScreenData(HttpServletRequest request,
                                       HttpServletResponse response,
                                       ScreensManager screensManager,
                                       SessionData data) {
        ServletContext context = request.getServletContext();
        Configuration configuration = (Configuration)context.getAttribute("configuration");
        SessionFactory factory = (SessionFactory)(request.getServletContext().getAttribute("sessionFactory"));

        LinksConfiguration linksConfiguration = configuration.getLinksConfiguration();
        LinksManager linksManager = new LinksManagerNonCaching(factory, linksConfiguration, (Collator)(request.getServletContext().getAttribute("monitoringCollator")));
        List<LinksEntityExtended> links = linksManager.getAllLinks();

        // Convert list of objects to JSON array of records to pass to front end
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        linksData.setJsonString(gson.toJson(links));

        return new RequestResult(request, getJSPname(), false);
    }

    public LinksDataBean getLinksData() {
        return linksData;
    }

}
