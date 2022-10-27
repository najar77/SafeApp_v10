package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.sun.mail.util.MailConnectException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.awt.*;
import java.io.IOException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

//import java.util.TimerTask;


public class faceD {
    public static String inputOtp="";
    //static int timerFlag=0;
    public static boolean networkOK=true;
    String OTP="";
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    public static String key="Mary has one cat";

    Main m1=new Main();

    @FXML
    public AnchorPane facedMain;
    public Button emailBtn;
    public Button forgetPassword;
    public Button newPasBtn;
    public TextField newPasTxt;
    public TextField emailTxt;
    public Button faceDataBtn;
    public TextField oldPas;
    public Button oldPasCnf;
    public TextField veri;
    public TextField otpField;
    public Button otpSubmit;
    public Button pathBtn;

    public void setPath()
    {
        try {
            FileChooser fileChooser=new FileChooser();
            File selectedFile = fileChooser.showOpenDialog(null);
            String em = selectedFile.getParentFile().toString();
            File f1=new File("C:\\SafeApp\\path.txt");
            if (f1.createNewFile())
            {
                System.out.println("1");
            }
            else
            {
                System.out.println("0");
            }
            Properties p1=new Properties();
            InputStream is1=new FileInputStream(f1);
            p1.load(is1);
            p1.setProperty("path",em);
            OutputStream os1=new FileOutputStream(f1);
            p1.store(os1,"Address");

            String path=em+"\\config.properties";
            File file = new File(path);
            if (file.createNewFile())
            {
                System.out.println("1");
            }
            else
            {
                System.out.println("0");
            }
            InputStream inputStream = new FileInputStream(file);
            Properties prop = new Properties();
            prop.load(inputStream);
            prop.setProperty("path",path);
            if(!prop.containsKey("password")) {
                prop.setProperty("password", enc("123456",100));
            }
            else {
                prop.setProperty("password", prop.getProperty("password"));
            }
            OutputStream outputStream=new FileOutputStream(file);
            prop.store(outputStream,"Data");
            //System.out.println(prop.getProperty("password"));
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Path Setup");
            alert.setContentText("ERROR");
            alert.show();
            System.out.println("error");
        }
    }

    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void setEmail()
    {
        try {
            String em = emailTxt.getText();
            if (isValid(em)){
                File f2 = new File("C:\\SafeApp\\path.txt");
                InputStream is2 = new FileInputStream(f2);
                Properties p2 = new Properties();
                p2.load(is2);
                String s2 = p2.getProperty("path");
                Path path = Paths.get(s2 + "\\config.properties");

                File file = new File(path.toString());
                InputStream inputStream = new FileInputStream(file);
                Properties prop = new Properties();
                prop.load(inputStream);
                prop.setProperty("email", em);
                OutputStream outputStream = new FileOutputStream(file);
                prop.store(outputStream, "Data");
                emailTxt.clear();
                emailTxt.setPromptText(prop.getProperty("email"));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Email Setup");
                alert.setContentText("DONE");
                alert.show();
            }
            else {
                emailTxt.clear();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Email Setup");
                alert.setContentText("Type Valid Email Address");
                alert.show();
            }
        }
        catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Email Setup");
            alert.setContentText("ERROR");
            alert.show();
            System.out.println("error");
        }

    }

    public void check() throws IOException {

        File f2=new File("C:\\SafeApp\\path.txt");
        InputStream is2=new FileInputStream(f2);
        Properties p2=new Properties();
        p2.load(is2);
        String s2=p2.getProperty("path");
        Path path= Paths.get(s2+"\\config.properties");

        File file=new File(path.toString());
        InputStream inputStream=new FileInputStream(file);
        Properties prop=new Properties();
        prop.load(inputStream);

        if(oldPas.getText().equals(dec(prop.getProperty("password"),100)))
        {
            newPasBtn.setVisible(true);
            veri.setVisible(false);
            emailBtn.setVisible(true);
            newPasTxt.setVisible(true);
            newPasBtn.setVisible(true);
            emailTxt.setVisible(true);
            faceDataBtn.setVisible(true);
            oldPas.setVisible(false);
            oldPasCnf.setVisible(false);
            forgetPassword.setVisible(false);
            otpField.setVisible(false);
            otpSubmit.setVisible(false);
            emailTxt.setPromptText(prop.getProperty("email"));
        }
        else {
            veri.setVisible(true);
            oldPas.clear();
        }
    }
    public  void createPas() throws IOException // not create password action function
    {
        encryptDecrypt(0);
        File f2=new File("C:\\SafeApp\\path.txt");
        InputStream is2=new FileInputStream(f2);
        Properties p2=new Properties();
        p2.load(is2);
        String s2=p2.getProperty("path");
        String comm=s2+"\\src\\sample\\f1.peg";
        /*
        String comm="py "+s2+"\\src\\sample\\f1.peg";
        "py C:\\Users\\Rajan\\face1\\src\\sample\\f1.py"
        Process p= Runtime.getRuntime().exec(comm);*/

        encryptDecrypti(0);
        ProcessBuilder pb = new ProcessBuilder("py",comm).inheritIO();
        Process p = pb.start();
        while (p.isAlive())
        {
            //do nothing
        }
        encryptDecrypti(1);
         encryptDecrypt(1);
    }

    public void updatePassword() {
        try {
            String s=newPasTxt.getText();
            File f2=new File("C:\\SafeApp\\path.txt");
            InputStream is2=new FileInputStream(f2);
            Properties p2=new Properties();
            p2.load(is2);
            String s2=p2.getProperty("path");
            Path path= Paths.get(s2+"\\config.properties");

            File file = new File(path.toString());
            InputStream inputStream = new FileInputStream(file);
            Properties prop = new Properties();
            prop.load(inputStream);
            prop.setProperty("password", enc(s,100));
            OutputStream outputStream = new FileOutputStream(file);
            prop.store(outputStream, "Data");
            newPasTxt.clear();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Account Setup");
            alert.setContentText("PASSWORD UPDATED");
            alert.show();
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Account Setup");
            alert.setContentText("ERROR");
            alert.show();
            System.out.println("error");
        }
    }

    public void mainP(ActionEvent event) throws Exception
    {
        Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        m1.start(primaryStage);

    }
    public void exit()
    {
        Platform.exit();
        System.exit(0);
    }
    public void forgetPassword() throws IOException {
        while (OTP.length()<6) {
            int pin = (int) (Math.random() * 1000000);
            OTP = String.valueOf(pin);
        }

        File f2=new File("C:\\SafeApp\\path.txt");
        InputStream is2=new FileInputStream(f2);
        Properties p2=new Properties();
        p2.load(is2);
        String s2=p2.getProperty("path");
        Path path= Paths.get(s2+"\\config.properties");

        File file=new File(path.toString());
        InputStream inputStream=new FileInputStream(file);
        Properties prop=new Properties();
        prop.load(inputStream);
        //System.out.println(prop.getProperty("password"));

        final String fromEmail = "app777my@gmail.com"; //requires valid gmail id
        final String password = "rajan@123"; // correct password for gmail id
        final String toEmail =prop.getProperty("email"); // can be any email id
        final String host = "smtp.gmail.com";
        String text = "hello your otp is " + OTP;
        System.out.println("Email Start");

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
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
            message.setSubject("OTP");
            message.setText(text);
            //send the message
            try {
                /*Thread t=new Thread(new sendOtp(message));
                t.start();
                t.join();*/
                Transport.send(message);
                System.out.println("message sent successfully...");
                networkOK = true;
                otpSubmit.setVisible(true);
                otpField.setVisible(true);
                forgetPassword.setVisible(false);

            } catch (Exception e) {
                Toolkit.getDefaultToolkit().beep();
                System.out.println("check network");
                networkOK = false;
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("NETWORK ERROR");
                alert.setContentText("PLEASE CHECK YOUR INTERNET CONNECTION");
                alert.show();
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void verifyOtp()
    {
        if (networkOK) {
           // Timer timer = new Timer();
            //timer.schedule(task, 300 * 1000);

            //Scanner scanner = new Scanner(System.in);
            //System.out.println("enter otp:");

            //for (int errorCount = 0; errorCount < 3 && timerFlag == 0; errorCount++)

            {
                //inputOtp = scanner.next();
                inputOtp=otpField.getText();
                if (OTP.equals("") || !inputOtp.equals(OTP) ) {
                    Toolkit.getDefaultToolkit().beep();
                    System.out.println("Wrong OTP");
                    System.out.println("rewrite otp");
                    veri.setVisible(true);
                    otpField.clear();
                    inputOtp="";
                } else {
                    System.out.println("otp matched");
                    newPasBtn.setVisible(true);
                    veri.setVisible(false);
                    emailBtn.setVisible(true);
                    newPasTxt.setVisible(true);
                    newPasBtn.setVisible(true);
                    emailTxt.setVisible(true);
                    faceDataBtn.setVisible(true);
                    oldPas.setVisible(false);
                    oldPasCnf.setVisible(false);
                    forgetPassword.setVisible(false);
                    otpSubmit.setVisible(false);
                    otpField.setVisible(false);
                    //emailTxt.setPromptText(prop.getProperty("email"));
                    //break;
                }
            }
            //timer.cancel();
        }
    }/*

    static TimerTask task = new TimerTask()
    {
        public void run()
        {
            if( inputOtp.equals("") )
            {
                System.out.println( "you input nothing." );
                timerFlag=1;
                //System.exit( 0 );
            }
        }
    };*/
    public String enc(String s1,int len)
    {
        StringBuffer sb=new StringBuffer();
        for (int i=0;i<s1.length();i++)
        {
            char c=s1.charAt(i);
            if(i%2==0){
                if(c==126)
                    c=(char) (33);
                else{
                    c=(char) (c+1);}
                sb.append(c);}
            else
                sb.append(c);
        }


        String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String Small_chars = "abcdefghijklmnopqrstuvwxyz";
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?<>)";

        String values = Capital_chars + Small_chars +
                numbers + symbols;

        Random rndm_method = new Random();

        StringBuffer password1 = new StringBuffer();

        for (int i = 0; i < len; i++)
        {
            password1.append(values.charAt(rndm_method.nextInt(values.length())));
        }
        Random rndm_method2 = new Random();

        StringBuffer password2 = new StringBuffer();

        for (int i = 0; i < len; i++)
        {
            password2.append(values.charAt(rndm_method2.nextInt(values.length())));
        }
        String s =password1.toString()+sb.toString()+password2.toString();
        return s;
    }
    public  String dec(String s1,int len)
    {
        try {
            int l = s1.length() - len;
            s1 = s1.substring(len, l);
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < s1.length(); i++) {
                char c = s1.charAt(i);
                if (i % 2 == 0) {
                    if (c == 33)
                        c = (char) (126);
                    else {
                        c = (char) (c - 1);
                    }
                    sb.append(c);
                } else
                    sb.append(c);
            }
            return sb.toString();
        }catch (Exception e)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Error Occured");
            alert.show();
            oldPas.clear();
            return null;
        }
    }
    public static void encryptDecrypt(int a) throws IOException {
        File f2=new File("C:\\SafeApp\\path.txt");
        InputStream is2=new FileInputStream(f2);
        Properties p2=new Properties();
        p2.load(is2);
        String s2=p2.getProperty("path");
        String initialSt=s2+"\\src\\sample";
        String enFolderSt=s2+"\\src\\sample";
        File initial = new File(initialSt);
        File enFolder =new File(enFolderSt);

        try {
            if(a==1) //encrypt
            {
                String nameF=initialSt+"\\f1.peg";
                File encryptedFile = new File(enFolderSt+"\\f1.ted");
                File inputFile =new File(nameF);
                encrypt(key, inputFile, encryptedFile);

                Files.setAttribute(encryptedFile.toPath(),"dos:hidden",true);

            }
            else { //decrypt

                String nameF=initialSt+"\\f1.ted";
                File encryptedFile =new File(nameF);
                if(encryptedFile.isFile())
                {
                    File decryptedFile = new File(enFolderSt+"\\f1.peg");

                    decrypt(key, encryptedFile, decryptedFile);
                    Files.setAttribute(decryptedFile.toPath(), "dos:hidden", false);
                }}
        } catch (CryptoException | IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static void encrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
        if(inputFile.delete())
        {
            //System.out.println("file deleted");
        }
        else
        {
            System.out.println("error in deletion");
        }
    }

    public static void decrypt(String key, File inputFile, File outputFile)
            throws CryptoException {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
        if (inputFile.delete())
        {
            //System.out.println("file deleted");
        }
        else
        {
            System.out.println("deletion failed");
        }
    }

    private static void doCrypto(int cipherMode, String key, File inputFile,
                                 File outputFile) throws CryptoException {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);
            FileOutputStream outputStream = new FileOutputStream(outputFile);

            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                | InvalidKeyException | BadPaddingException
                | IllegalBlockSizeException | IOException ex) {
            throw new CryptoException("Error encrypting/decrypting file", ex);
        }
    }

    // for saved Image

    public static void encryptDecrypti(int a) throws IOException {
        File f2=new File("C:\\SafeApp\\path.txt");
        InputStream is2=new FileInputStream(f2);
        Properties p2=new Properties();
        p2.load(is2);
        String s2=p2.getProperty("path");
        String initialSt=s2+"\\src\\sample\\savedImg\\";
        String enFolderSt=s2+"\\src\\sample\\savedImg\\";
        File initial = new File(initialSt);
        File enFolder =new File(enFolderSt);

        try {
            if(a==1) //encrypt
            {
                File[] listof= initial.listFiles();
                for (int i=0;i<listof.length;i++)
                {
                    String nameF=listof[i].toString();
                    File encryptedFile = new File(enFolderSt+listof[i].getName()+".encrypted");
                    File inputFile =new File(nameF);
                    int index=listof[i].getName().lastIndexOf(".");
                    if(!listof[i].getName().substring(index).equals(".encrypted"))
                    {
                        encrypt(key, inputFile, encryptedFile);
                        Files.setAttribute(encryptedFile.toPath(),"dos:hidden",true);
                    }
                }
            }
            else { //decrypt
                File[] listOfDec=enFolder.listFiles();
                for (int i = 0; i < listOfDec.length; i++) {
                    File file = listOfDec[i];
                    String nameF = file.getName();
                    File encryptedFile = new File(enFolderSt + nameF);
                    int index = nameF.lastIndexOf(".");
                    String finalName = initialSt + nameF;
                    if (index != -1)
                        finalName = initialSt + nameF.substring(0, index);

                    File decryptedFile = new File(finalName);
                    if (nameF.substring(index).equals(".encrypted")) {
                        decrypt(key, encryptedFile, decryptedFile);
                        Files.setAttribute(decryptedFile.toPath(), "dos:hidden", false);
                    }
                }
            }
        } catch (CryptoException | IOException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }
    }


}
