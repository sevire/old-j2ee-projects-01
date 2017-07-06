package co.uk.genonline.simpleweb.configuration.configitems;

/**
 * Created by thomassecondary on 15/03/2017.
 */
public class LinkDaysBetweenChecks extends ConfigurationItemInt {
    LinkDaysBetweenChecks(int value) {
        super(value);
    }

    public LinkDaysBetweenChecks(String value) {
        super(value);
    }

    public String getName() {
        return "linkDaysBetweenChecks";
    }
}
