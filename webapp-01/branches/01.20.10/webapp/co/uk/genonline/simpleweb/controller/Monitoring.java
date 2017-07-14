package co.uk.genonline.simpleweb.controller;

import co.uk.genonline.simpleweb.monitoring.Collator;
import com.sun.jersey.api.provider.jaxb.XmlHeader;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import java.util.List;

/**
 * Created by thomassecondary on 04/06/2017.
 */
@Path("/monitoring")
public class Monitoring {

    @Context
    private ServletContext context;

    @GET
    @Produces("application/xml")
    @XmlHeader(value = "<?xml-stylesheet type='application/xml' href='../monitor-presentation.xsl' ?>")
    public Collator monitoring() {
        Collator collator = (Collator)context.getAttribute("monitoringCollator");

        return collator;
    }
}