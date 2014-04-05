package co.uk.genonline.simpleweb.configuration.general;

import co.uk.genonline.simpleweb.controller.WebLogger;

/**
 * Created with IntelliJ IDEA.
 *
 * @User: thomassecondary
 * @Date: 05/07/2013
 * @Time: 11:32
 */
public abstract class ConfigurationItem {
    protected final WebLogger logger = new WebLogger();

    private String value; // Note: value will be overridden in subclass depending upon type unless String

    /**
     * Always have a constructor which takes a String value.  When sub-classing, add constructor for type of sub-class.
     *
     * @param value value of the ConfigurationItem.
     */
    public ConfigurationItem(String value) {
        this.value = value;
    }

    /**
     * No parameter constructor will be called by sub-classes.  Just logs message identifying name of config item.
     */

    protected ConfigurationItem() {
        logger.debug(String.format("Instantiating <%s>", getName()));
    }

    // Type dependent constructor will need to be added in sub-class.

    /**
     * Defines and returns name of this object / config item.  There is no member for name, just a getter.  This is so that
     * in sub-classes the value can be overridden rather than hidden which can cause problems.  The only reason to override at
     * each degree of sub-classing is to help in debugging.  In base class (this one) the value is set to "uninitialised".
     * In lowest level sub-class it will be the name of the config item.  In intermediate levels it may be set to the type
     * of the general purpose sub-class (such as int, bool etc).
     *
     * @return Always returns "uninitialised" to help detect whether subclasses haven't overridden the constructor
     */
    public String getName() {
        return "uninitialised";
    }

    // Type dependent getter needs to be added on sub-class

    public abstract String getStringValue();
}
