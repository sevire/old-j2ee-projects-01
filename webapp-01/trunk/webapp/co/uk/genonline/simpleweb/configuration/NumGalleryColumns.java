package co.uk.genonline.simpleweb.configuration;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 19/06/2013
 * Time: 08:28
 * To change this template use File | Settings | File Templates.
 */
public class NumGalleryColumns implements ConfigItem {
    int numGalleryColumns;
    final int defaultValue = 4;
    final int minValue = 1;
    final int maxValue = 10;

    NumGalleryColumns(String value) {
        set(value);
    }
    public Object get() {
        return new Integer(numGalleryColumns);
    }

    public void set(String value) {
        int convertedValue = defaultValue;

        value.trim();
        try {
            convertedValue = Integer.parseInt(value);
        } catch (java.lang.NumberFormatException e) {
            numGalleryColumns = defaultValue;
        }
        if (convertedValue < minValue) {
            numGalleryColumns = minValue;
        } else if (convertedValue > maxValue) {
            numGalleryColumns = maxValue;
        }
    }
}
