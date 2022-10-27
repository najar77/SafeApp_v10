package sample;

import com.sun.mail.util.MailConnectException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class ContactUs extends Thread {

    Main m1=new Main();
    Controller c1=new Controller();

    @FXML
    public Button back;
    public Button send;
    public Button exit;
    public TextArea text1;



    public void send() throws IOException {
        String s=text1.getText();
        Runnable r = new sendMail(s);
        new Thread(r).start();
    }
    public void back(ActionEvent event) throws Exception {
        if(Main.unlocked)
        {
            //Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            c1.backstorage(event);
        }
        else {
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            m1.start(primaryStage);
        }
    }
    public void exit()
    {
        Platform.exit();
        System.exit(0);
    }
}
