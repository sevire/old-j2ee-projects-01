package co.uk.genonline.simpleweb.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by thomassecondary on 21/02/16.
 */
public class SMTPAuthenticator extends Authenticator {
    private String username;
    private String password;

    public SMTPAuthenticator(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        PasswordAuthentication authentication = new PasswordAuthentication(username, password);
        return authentication;
    }
}
