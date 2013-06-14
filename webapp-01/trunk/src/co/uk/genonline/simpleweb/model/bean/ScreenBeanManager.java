package co.uk.genonline.simpleweb.model.bean;

/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 17/12/2012
 * Time: 14:35
 *
 * Not sure whether this is the best way to do this.  I want to carry out some activities on a bean (such as initialising)
 * but I don't want to modify the bean declaration as this is reverse engineered from the database schema.  So I am
 * creating this utility class as a layer of management.
 *
 * This should really be generic and be usable for any bean but I don't want to invest the time for that now and in
 * practice I only need to worry about Screens.
 */
public class ScreenBeanManager {
    Screens screenBean;

    public ScreenBeanManager(Screens screenBean) {
        this.screenBean = screenBean;
    }

    public void initialiseBean() {
        screenBean.setEnabledFlag(true);
        screenBean.setGalleryFlag(false);
        screenBean.setScreenContents("");
        screenBean.setMetaDescription("");
        screenBean.setName("");
        screenBean.setScreenTitleLong("");
        screenBean.setScreenTitleShort("");
        screenBean.setScreenType("Mistress");
        screenBean.setSortKey(100);

        screenBean.setId(0);
    }
}
