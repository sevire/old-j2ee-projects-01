package co.uk.genonline.simpleweb.controller.screendata;

import co.uk.genonline.simpleweb.controller.SessionData;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.controller.screendata.displaybeans.ScreenMistressTableBean;
import co.uk.genonline.simpleweb.model.bean.ScreensEntity;
import co.uk.genonline.simpleweb.model.bean.ScreensManager;
import com.hp.gagawa.java.elements.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


/**
 * Created with IntelliJ IDEA.
 * User: thomassecondary
 * Date: 24/10/2013
 * Time: 18:42
 * To change this template use File | Settings | File Templates.
 */
public class MistressIndexScreenData extends MistressScreenData {
    private ScreenMistressTableBean screenMistressTable = new ScreenMistressTableBean();

    public MistressIndexScreenData(String screenType) {
        super(screenType);
    }

    /**
     * NOTE:
     *
     * This is a step in the journey to refactor MistressScreenData to be a little more structured and modular.  I have
     * overridden setScreenDetailsData from MistressScreenData but have copied all the code across so that I can add in the
     * code to create the MistressIndex table.  This still repeats code but not as badly as if I hadn't re-factored
     * MistressScreenData.  The next step would be to put more of this code in methods so I'm not repeating it.
     *
     * @param request        Request object from Tomcat container
     * @param response       Response object from Tomcat container
     * @param screensManager Object used to access the Screens records in the database
     * @param data           Data persisted within session between requests
     * @return
     */
    public RequestResult setScreenData(HttpServletRequest request,
                                       HttpServletResponse response,
                                       ScreensManager screensManager,
                                       SessionData data
    ) {
        init(request, response, screensManager, data);

        if (screenRecord == null) {
            logger.warn(String.format("View page: Couldn't retrieve page <%s>", screenName));
            response.setStatus(404);
            return new RequestResult(request, "error.jsp", false);
        } else {
            if (screenRecord.getEnabledFlag()) {
                setScreenDetailsData();
                setScreenHeader();
                setScreenMenus();
                setScreenGallery();
                setScreenMistressTable();

                return new RequestResult(request, getJSPname(), false);
            } else {
                logger.info(String.format("Screen disabled, treating like non-existent page <%s>", screenRecord.getName()));
                response.setStatus(404);
                return new RequestResult(request, "error.jsp", false);
            }
        }
    }

    public ScreenMistressTableBean getScreenMistressTable() {
        return screenMistressTable;
    }

    public void setScreenMistressTable() {
        List<ScreensEntity> mistressScreens = screensManager.getScreensByType("Mistress", false);

        Table tableElement = new Table();
        tableElement.setCSSClass("mistressLinks");
        for (ScreensEntity screen : mistressScreens) {
            Tr tableRow = new Tr();

            Td imageCell = new Td();
            imageCell.setCSSClass("imageCell");

            Td linkCell = new Td();
            linkCell.setCSSClass("linkCell");

            Img imageElement = new Img(screen.getName(), "site_images/" + screen.getName() + ".jpg");
            imageCell.appendChild(imageElement);

            A mistressLink = new A("view?screen="+screen.getName());
            mistressLink.appendText(screen.getScreenTitleShort());
            mistressLink.setCSSClass("mistressLink");
            linkCell.appendChild(mistressLink);

            tableRow.appendChild(imageCell);
            tableRow.appendChild(linkCell);

            tableElement.appendChild(tableRow);
        }
        String Html = tableElement.write();
        screenMistressTable.setMistressTable(Html);
    }
}
