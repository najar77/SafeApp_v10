package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;


public class storagePage {

    @FXML
    public MenuBar menubar;
    private static final String ALGORITHM = "AES";
    private static final String TRANSFORMATION = "AES";
    public static String key="Mary has one cat";
    public Button move,open_folder,exit,backToMain;
    Main m1=new Main();

    private void moves(String b) throws IOException {
        String s;
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);

        Path temp = null;
        s=selectedFile.getName();
        int flag=0;
        int flag2=0;
        int count =0;

        File f2=new File("C:\\SafeApp\\path.txt");
        InputStream is2=new FileInputStream(f2);
        Properties p2=new Properties();
        p2.load(is2);
        String s2=p2.getProperty("path");

        while(flag==0) {
            try {
                if(count==0)
                temp = Files.move
                        (Paths.get(selectedFile.getPath()),
                                Paths.get(s2+"\\Final_location\\" + s + ""));
                else
                {
                    flag2=1;
                    String s1=Integer.toString(count);
                    temp = Files.move
                            (Paths.get(selectedFile.getPath()),
                                    Paths.get(s2+"\\Final_location\\"+s1 + s + ""));
                }
            } catch (FileAlreadyExistsException e) {
                count++;
                System.out.println("exist");
                continue;
            } catch (IOException e) {
                e.printStackTrace();
            }
            flag=1;
        }
        if(temp != null && flag2==1)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Move "+b);
            alert.setContentText("File renamed and moved successfully");
            alert.show();
        }
        else if(temp != null)
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Move "+b);
            alert.setContentText("File moved successfully");
            alert.show();
        }
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Move "+b);
            alert.setContentText("Failed to move the file");
            alert.show();
        }
    }
    public void openFolder()
    {
            try {
                File f2=new File("C:\\SafeApp\\path.txt");
                InputStream is2=new FileInputStream(f2);
                Properties p2=new Properties();
                p2.load(is2);
                String s2=p2.getProperty("path");
                String comm="explorer.exe "+s2+"\\Final_location";
                //"explorer.exe C:\\Users\\Rajan\\face1\\Final_location"
                Runtime.getRuntime().exec(comm);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }
    public void main1() throws IOException {
        moves("Images");
    }

    public void mainP(ActionEvent event) throws Exception
    {
        Main.unlocked=false;
        storagePage.encryptDecrypt(1);
        Stage primaryStage=(Stage)((Node)event.getSource()).getScene().getWindow();
        m1.start(primaryStage);
    }

    public static void encryptDecrypt(int a) throws IOException {
        File f2=new File("C:\\SafeApp\\path.txt");
        InputStream is2=new FileInputStream(f2);
        Properties p2=new Properties();
        p2.load(is2);
        String s2=p2.getProperty("path");
        String initialSt=s2+"\\Final_location\\";
        String enFolderSt=s2+"\\Final_location\\";
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

    public void exit() throws IOException {
        Main.unlocked=false;
        encryptDecrypt(1);
        Platform.exit();
        System.exit(0);
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
        assert root1 != null;
        primaryStage.setScene(new Scene(root1, 600, 350));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

}
