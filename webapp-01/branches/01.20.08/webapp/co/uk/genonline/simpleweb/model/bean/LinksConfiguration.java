package co.uk.genonline.simpleweb.model.bean;

/**
 * Created by thomassecondary on 15/03/2017.
 */
public class LinksConfiguration {
    private final int daysBetweenChecks;
    private final String linksRoot;

    public LinksConfiguration(int daysBetweenChecks, String linksRoot) {
        this.daysBetweenChecks = daysBetweenChecks;
        this.linksRoot = linksRoot;
    }

    public int getDaysBetweenChecks() {
        return daysBetweenChecks;
    }

    public String getLinksRoot() {
        return linksRoot;
    }
}
