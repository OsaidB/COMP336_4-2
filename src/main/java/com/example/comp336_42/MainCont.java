package com.example.comp336_42;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

import static com.example.comp336_42.Main.primaryStage;

public class MainCont {


    public void againstPC_onAction() {
        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("AgainstPC.fxml"));
            //to choose who play first
            Parent root = loader.load();
           Scene scene= new Scene(root);


            URL cssURL = Main.class.getResource("myStyle.css");
            if (cssURL != null) {
                scene.getStylesheets().add(cssURL.toExternalForm());
            } else {
                System.err.println("CSS file not found.");
            }

//            scene.getStylesheets().add("/myStyle.css");
            primaryStage.setScene(scene);
//            primaryStage.setScene(new Scene(root));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void againstFriend_onAction() {
        try {

            FXMLLoader loader = new FXMLLoader(Main.class.getResource("AgainstFriend.fxml"));
            //to choose who's X and who's O
            //and to assign the name of each of them
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
