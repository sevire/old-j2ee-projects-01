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

    public MistressIndexScreenData(String screenType) {
        super(screenType);

        mistressScreenDataFlags = new MistressScreenDataFlags(
                true,
                true,
                true,
                true,
                true,
                true,
                false
        );
    }
}
