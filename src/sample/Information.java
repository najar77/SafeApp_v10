package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Information {
    Main m1=new Main();
    Controller c1=new Controller();


    public void back(ActionEvent event) throws Exception {
        if(Main.unlocked==true)
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
