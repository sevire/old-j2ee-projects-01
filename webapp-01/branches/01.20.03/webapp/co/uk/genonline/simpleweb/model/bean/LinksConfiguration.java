package co.uk.genonline.simpleweb.model.bean;

/**
 * Created by thomassecondary on 15/03/2017.
 */
public class LinksConfiguration {
    private final int daysBetweenChecks;

    public LinksConfiguration(int daysBetweenChecks) {
        this.daysBetweenChecks = daysBetweenChecks;
    }

    public int getDaysBetweenChecks() {
        return daysBetweenChecks;
    }
}
