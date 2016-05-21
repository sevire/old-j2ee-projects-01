package co.uk.genonline.simpleweb.controller.actions.simpleactions;

import co.uk.genonline.simpleweb.controller.actions.Action;
import co.uk.genonline.simpleweb.controller.actions.RequestResult;
import co.uk.genonline.simpleweb.mail.SMTPAuthenticator;
import co.uk.genonline.simpleweb.mail.SMTPDetails;
import co.uk.genonline.simpleweb.mail.SendMail;

import javax.mail.Authenticator;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by thomassecondary on 14/03/16.
 */
public class ContactMeProcessForm extends Action {
    public ContactMeProcessForm(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    public RequestResult perform() {
        String name;
        String emailAddress;
        String reason;
        String message;

        name = request.getParameter("name");
        emailAddress = request.getParameter("email");
        reason = request.getParameter("reason");
        message = request.getParameter("message");

        String emailMessage = "Name: " + name + "\n" +
                "Email: " + emailAddress + "\n" +
                "Reason: " + reason + "\n" +
                "Message" + message + "\n";

        Authenticator authenticator = new SMTPAuthenticator("sevire+genonline.co.uk","avoids");
        SMTPDetails emailDetails = new SMTPDetails("newport.theukhost.net", true, "sevire@genonline.co.uk", authenticator);

        SendMail sendMail = new SendMail();

        List<String> toEmailAddresses = new ArrayList<String>(Arrays.asList(
                "lucinaslittleboy@genonline.co.uk"
        ));
        sendMail.sendMessage(emailDetails.getMailSession(),
                emailDetails.getFromEmailAddress(),
                toEmailAddresses,
                "Contact Me Email From Website",
                emailMessage);
        return new RequestResult(request, "/view?screen=contactMe", true);
    }
}
