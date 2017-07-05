package co.uk.genonline.simpleweb.monitoring;

/**
 * Created by thomassecondary on 03/06/2017.
 */
public enum CollectableCategory {
    LINK("Link"),
    GALLERY("Gallery"),
    DATABASE("Database");

    private String categoryDescription;

    CollectableCategory(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }
}
