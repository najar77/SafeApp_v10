package sample;

import com.sun.mail.util.MailConnectException;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class sendMail implements Runnable {

    public String tx;
    boolean flag= false;
    Alert alert=new Alert(Alert.AlertType.INFORMATION);
    ContactUs contactUs=new ContactUs();

    public sendMail(String s)
    {
        tx=s;
    }

    public void run()
    {
        File f2=new File("C:\\SafeApp\\path.txt");
        InputStream is2= null;
        try {
            is2 = new FileInputStream(f2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties p2=new Properties();
        try {
            p2.load(is2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String s2=p2.getProperty("path");
        Path path= Paths.get(s2+"\\config.properties");

        File file=new File(path.toString());
        InputStream inputStream= null;
        try {
            inputStream = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Properties prop=new Properties();
        try {
            prop.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        final String fromEmail = "app777my@gmail.com"; //requires valid gmail id
        final String password = "rajan@123"; // correct password for gmail id
        final String toEmail1 ="rajansingh7797@gmail.com";
        final String toEmail2 ="mishrakeshav218@gmail.com"; //

        final String host = "smtp.gmail.com";
        String text ="from \n"+ prop.getProperty("email")+"\n \n"+tx;
        System.out.println("Email Start");

        Platform.runLater(new Runnable( ) {
            @Override
            public void run() {
                flag=false;
                alert.setContentText("Wait, You will be notified soon...");
                alert.show();

            }
        });

        Properties props = new Properties();

        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", "587");

        //create Authenticator object to pass in Session.getInstance argument
        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(fromEmail, password);
                    }
                });
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail1));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail2));
            message.setSubject("Query from safeApp");
            message.setText(text);

            //send the message
            try {
                Transport.send(message);
                System.out.println("message sent successfully...");
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        alert.close();
                        Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                        alert2.setTitle("Sent");
                        alert2.setContentText("Query sent, we will reach back soon");
                        alert2.show();

                    }
                });


            } catch (MailConnectException e) {
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        flag=true;
                        alert.close();
                        Toolkit.getDefaultToolkit().beep();
                        System.out.println("check network");
                        Alert alert1=new Alert(Alert.AlertType.INFORMATION);
                        alert1.setTitle("NETWORK ERROR");
                        alert1.setContentText("PLEASE CHECK YOUR INTERNET CONNECTION");
                        alert1.show();
                    }
                });
            }


        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

}
