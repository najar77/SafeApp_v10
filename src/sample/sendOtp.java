package sample;

import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

public class sendOtp extends Thread {

    MimeMessage m;
    public sendOtp(MimeMessage message) {
        m=message;
    }

    @Override
    public void run() {
        try {
            Transport.send(m);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
