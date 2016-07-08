package co.uk.genonline.simpleweb.model.bean;

import co.uk.genonline.simpleweb.controller.WebLogger;
import com.petebevin.markdown.MarkdownProcessor;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Decorator for ScreensEntity which will allow operations on data within ScreensEntity without having to modify
 * the ScreensEntity class.  This is important as the ScreensEntity class is auto-generated by IDEA.
 * 
 * Main operations required are:
 * - Instantiate ScreensEntity in one call rather than a sequence of setXXX calls.
 * - Implement business rules around screen names, which are:
 *   o If long name is blank use short name.
 *   o If short name is blank use name.
 * - Parse Markdown text into HTML.
 */
public class ScreensEntityDecorator extends ScreensEntity {
    private WebLogger logger = new WebLogger();
    private ScreensEntity screen;

    public ScreensEntityDecorator(ScreensEntity screen) {
        this.screen = screen;
    }

    @Override
    public int getId() {
        return screen.getId();
    }

    @Override
    public int getParentId() {
        return screen.getParentId();
    }

    @Override
    public String getName() {
        return screen.getName();
    }

    @Override
    public String getScreenTitleLong() {
        if (screen.getScreenTitleLong() == null || screen.getScreenTitleLong().equals("")) {
            return getScreenTitleShort();
        } else {
            return screen.getScreenTitleLong();
        }
    }

    @Override
    public String getScreenTitleShort() {
        if (screen.getScreenTitleShort() == null || screen.getScreenTitleShort().equals("")) {
            return getName();
        } else {
            return screen.getScreenTitleShort();
        }
    }

    @Override
    public String getScreenContents() {
        return screen.getScreenContents();
    }

    /**
     * Uses Markdown pre-processor to parse screen text from Markdown into HTML.
     *
     * @return HTML produced from parsing Markdown markup.
     */
    public String getScreenContentsHtml() {
        MarkdownProcessor markdownDecoder = new MarkdownProcessor();

        logger.debug("About to parse page with Markdown");
        String pageText = screen.getScreenContents();

        logger.debug("Markdown Input text is " + pageText.substring(0, Math.min(39, pageText.length())) + "...");
        String HTML = markdownDecoder.markdown(pageText);

        logger.debug("Markdown Output HTML is " + HTML.substring(0, Math.min(39, HTML.length())) + "...");

        return HTML;
    }

    @Override
    public String getMetaDescription() {
        return screen.getMetaDescription();
    }

    @Override
    public Boolean getEnabledFlag() {
        return screen.getEnabledFlag();
    }

    @Override
    public Boolean getGalleryFlag() {
        return screen.getGalleryFlag();
    }

    @Override
    public Timestamp getCreated() {
        return screen.getCreated();
    }

    @Override
    public Timestamp getModified() {
        return screen.getModified();
    }

    @Override
    public String getScreenType() {
        return screen.getScreenType();
    }

    @Override
    public Integer getSortKey() {
        return screen.getSortKey();
    }

    @Override
    public String getScreenDisplayType() {
        return screen.getScreenDisplayType();
    }

    public ScreensEntity getScreen() {
        return screen;
    }

    public void setScreen(ScreensEntity screen) {
        this.screen = screen;
    }

    public static List<ScreensEntityDecorator> decorateScreenList(List screenList) {
        List<ScreensEntityDecorator> decoratedScreenList = new ArrayList<ScreensEntityDecorator>();

        for (Object screen : screenList) {
            if (screen.getClass() == ScreensEntity.class) {
                decoratedScreenList.add(new ScreensEntityDecorator((ScreensEntity)screen));
            } else {
                // logger.error("Method decorateScreenList, object from Hibernate not ScreensEntity");
            }
        }
        return decoratedScreenList;
    }

    public String toString() {
        int contentLength = getScreenContents().length() > 10 ? 10 : getScreenContents().length();
        return String.format("ScreensEntity::id:%d, name:%s, enabled:%b, type:%s, short:%s, long:%s, display:%s, gallery:%b, text:<%s>",
                getId(),
                getName(),
                getEnabledFlag(),
                getScreenType(),
                getScreenTitleShort(),
                getScreenTitleLong(),
                getScreenDisplayType(),
                getGalleryFlag(),
                getScreenContents().substring(0, contentLength));
    }
}
