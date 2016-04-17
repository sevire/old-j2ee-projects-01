package co.uk.genonline.simpleweb.model.bean;

/**
 * Created by thomassecondary on 31/07/15.
 */
public enum ScreensSortType {
    DEFAULT(null),
    ADMIN_SCREEN("enabledFlag desc, screenType, sortKey, name"),
    LINKBAR("screenType, sortKey"),
    NAME_ORDER("name");

    private String sortClause;

    ScreensSortType(String sortClause) {
        this.sortClause = sortClause;
    }

    public String getSortClause() {
        if (sortClause == null || sortClause.isEmpty()) {
            return "";
        } else {
            return "order by " + sortClause;
        }
    }
}
