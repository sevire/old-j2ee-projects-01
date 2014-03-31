package co.uk.genonline.simpleweb.configuration.configitems;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 28/10/2013
 * Time: 13:07
 * To change this template use File | Settings | File Templates.
 */
public class BlogEnabled extends ConfigurationItemBoolean {
    BlogEnabled(boolean value) {
        super(value);
    }

    public BlogEnabled(String value) {
        super(value);
    }

    public String getName() {
        return "blogEnabled";
    }
}
