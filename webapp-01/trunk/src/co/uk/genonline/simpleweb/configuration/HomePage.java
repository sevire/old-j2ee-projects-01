package co.uk.genonline.simpleweb.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/06/2013
 * Time: 08:13
 * To change this template use File | Settings | File Templates.
 */
public class HomePage implements ConfigItem {
    String homePage;

    public HomePage(String value) {
        set(value); // No conversion or checking required so this is an easy one
    }

    public Object get() {
        return homePage;
    }

    public void set(String value) {
        homePage = value;
    }

}
