package co.uk.genonline.simpleweb.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;


/**
 * Created by thomassecondary on 21/02/16.
 */
public class SendMail {

    public void sendMessage(Session mailSession,
                            String fromEmailAddress,
                            List<String> toEmailAddresses,
                            String mailSubject,
                            String mailMessage) {
        mailSession.setDebug(false);
        Transport transport;
        try {
            transport = mailSession.getTransport();

            MimeMessage message = new MimeMessage(mailSession);
            message.setSubject(mailSubject);
            message.setContent(mailMessage, "text/plain");
            message.setFrom(new InternetAddress(fromEmailAddress));
            for (String toEmailAddress : toEmailAddresses) {
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmailAddress));
            }

            transport.connect();
            transport.sendMessage(message, message.getRecipients(Message.RecipientType.TO));
            transport.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
