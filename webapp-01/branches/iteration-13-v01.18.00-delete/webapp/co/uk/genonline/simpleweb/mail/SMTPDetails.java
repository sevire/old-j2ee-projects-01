package co.uk.genonline.simpleweb.mail;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Properties;

/**
 * Created by thomassecondary on 29/02/16.
 */
public class SMTPDetails {
    private String fromEmailAddress;
    private Session session;
    Authenticator authenticator;

    public SMTPDetails(String smtpHostname, boolean forceAuthorisation, String fromEmailAddress, Authenticator authenticator) {
        Properties props = new Properties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.host", smtpHostname);
        props.put("mail.smtp.auth", forceAuthorisation);
        this.fromEmailAddress = fromEmailAddress;
        this.authenticator = authenticator;
        session = Session.getDefaultInstance(props, authenticator);
    }

    public Session getMailSession() {
        return session;
    }

    public String getFromEmailAddress() {
        return fromEmailAddress;
    }
}
