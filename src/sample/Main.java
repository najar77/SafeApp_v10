package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends Application {

    public static boolean unlocked=false;

    @Override
    public void start(Stage primaryStage) throws Exception{

        Path p = Paths.get( "C:\\SafeApp\\");
        Files.createDirectories(p);
        unlocked=false;

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        primaryStage.setTitle("SAFE APP");
        primaryStage.setScene(new Scene(root, 600, 350));
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.setOnCloseRequest(we -> {
            try {
                storagePage.encryptDecrypt(1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Stage is closing");
        });

    }


    public static void main(String[] args) {
        launch(args);
    }


}
