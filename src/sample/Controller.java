package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.Random;


public class Controller {

    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    public static String key="Mary has one cat";

    @FXML
    public MenuItem acset;
    @FXML
    public MenuBar menubar;

    public TextField getPassword;
    public Button enterBtn;
    public TextField veri;
    Main m1=new Main();

    public void mb()
    {
        Stage primaryStage=(Stage) menubar.getScene().getWindow();
                Parent root1 = null;
                try {
                    root1 = FXMLLoader.load(getClass().getResource("faceD.fxml"));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                primaryStage.setTitle("Account Setup");
                primaryStage.setScene(new Scene(root1, 600, 350));
                primaryStage.setResizable(false);
                primaryStage.show();
    }
    public void infoF()
    {
        Stage primaryStage=(Stage) menubar.getScene().getWindow();
        Parent root1 = null;
        try {
            root1 = FXMLLoader.load(getClass().getResource("information.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Contact Us");
        primaryStage.setScene(new Scene(root1, 600, 350));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public  void contactF()
    {
        Stage primaryStage=(Stage) menubar.getScene().getWindow();
        Parent root1 = null;
        try {
            root1 = FXMLLoader.load(getClass().getResource("contactUs.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        primaryStage.setTitle("Contact Us");
        assert root1 != null;
        primaryStage.setScene(new Scene(root1, 600, 350));
        primaryStage.setResizable(false);
        primaryStage.show();

    }
    public void aboutF()
    {
        Alert alert=new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About Us");
        alert.setContentText("To be written");
        alert.show();
    }

    public void faceDetection(ActionEvent event) throws IOException {

            /*Parent root = FXMLLoader.load(getClass().getResource("faceD.fxml"));
            Scene Scene2 = new Scene(root);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(Scene2.);
            window.show();*/


        Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("faceD.fxml"));


        primaryStage.setTitle("Account");
        primaryStage.setScene(new Scene(root, 500, 300));
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    public void openStorageF(ActionEvent event) throws IOException
    {
        storagePage.encryptDecrypt(0);
        Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        Main.unlocked=true;
        Parent root = FXMLLoader.load(getClass().getResource("StoragePage.fxml"));
        primaryStage.setTitle("Storage");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public void openStorage(ActionEvent event) throws IOException
    {
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


        if(getPassword.getText().equals(dec(prop.getProperty("password"),100))) {
            storagePage.encryptDecrypt(0);
            Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Main.unlocked=true;
            Parent root = FXMLLoader.load(getClass().getResource("StoragePage.fxml"));
            primaryStage.setTitle("Storage");
            primaryStage.setScene(new Scene(root, 600, 350));
            primaryStage.setResizable(false);
            primaryStage.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Wrong Password");
            alert.setContentText("PLEASE ENTER CORRECT PASSWORD");
            alert.show();
            getPassword.clear();
        }
    }

    public void backstorage(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Main.unlocked=true;
        Parent root = FXMLLoader.load(getClass().getResource("StoragePage.fxml"));
        primaryStage.setTitle("Storage");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.setResizable(false);
        primaryStage.show();
    }



    public void startPy() throws IOException {

        veri.setVisible(false);
        try {

            enterBtn.setVisible(false);
            File f2=new File("C:\\SafeApp\\path.txt");
            InputStream is2=new FileInputStream(f2);
            Properties p2=new Properties();
            p2.load(is2);
            String s2=p2.getProperty("path");

            Path path= Paths.get(s2+"\\src\\sample\\f2.peg");

            int x;

            File directory = new File(s2+"\\src\\sample\\savedImg");
            if (directory.isDirectory()) {
                if (directory.length() > 0) {
                    encryptDecrypt(0);
                    encryptDecrypti(0);
                    ProcessBuilder pb = new ProcessBuilder("py",path.toString()).inheritIO();
                    Process p = pb.start();
            //encryptDecrypt(1);

            while(p.isAlive())
            {
                //do nothing
            }
            encryptDecrypti(1);
                    Path path1=Paths.get(s2,"src\\sample\\test.txt");
                    FileReader fr=new FileReader(path1.toString());
                    x=fr.read()-48;

                    int len =700;
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
                        if(i%100==0 && i!=0)
                            password1.append("\n");
                        else
                        password1.append(values.charAt(rndm_method.nextInt(values.length())));
                    }
                    FileWriter file=new FileWriter(path1.toString(),false);
                    file.write(password1.toString());
                    file.close();


            System.out.println(x);
            if(x==1)
            {
                enterBtn.setVisible(true);
            }
            else
            {
                veri.setVisible(true);
            }
        encryptDecrypt(1);
                } else {
                        Alert alert=new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Face data unavailable");
                        alert.show();
                }
            }
            else
            {
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Folder Missing, Click OK to create");
                alert.showAndWait();
                if (alert.getResult().equals(ButtonType.OK)) {
                    Path p = Paths.get(s2 + "\\src\\sample\\savedImg\\");
                    Files.createDirectories(p);

                }
            }
        }
        catch (Exception e) {
            System.out.println(e);
        }
    }

    public void exit() throws IOException {
        storagePage.encryptDecrypt(1);
        Platform.exit();
        System.exit(0);
    }
    public  String enc(String s1,int len)
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
        return password1.toString()+sb.toString()+password2.toString();
    }
    public  String dec(String s1,int len) {
        try {
            int l = s1.length() - len;
            s1 = s1.substring(len, l);
            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < s1.length(); i++) {
                char c = s1.charAt(i);
                if (i % 2 == 0) {
                    if (c == 33)
                        c = (char) (126);
                    else {
                        c = (char) (c - 1);
                    }
                }
                sb.append(c);
            }
            return sb.toString();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Error Occured");
            alert.show();
            getPassword.clear();
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

        try {
            if(a==1) //encrypt
            {
                System.out.println("hey");
                String nameF=initialSt+"\\f2.peg";
                File encryptedFile = new File(enFolderSt+"\\f2.ted");
                File inputFile =new File(nameF);
                encrypt(key, inputFile, encryptedFile);

                Files.setAttribute(encryptedFile.toPath(),"dos:hidden",true);

            }
            else { //decrypt

                String nameF=initialSt+"\\f2.ted";
                File encryptedFile =new File(nameF);
                if(encryptedFile.isFile())
                {
                File decryptedFile = new File(enFolderSt+"\\f2.peg");

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
            System.out.println(outputFile.getName());
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
