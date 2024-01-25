package com.example.comp336_42;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Stage primaryStage;

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        Parent root = loader.load();
        primaryStage = new Stage();
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }


    public static void ShowBoard() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("Board.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
    }


    public static void SwitchToLevelsScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("AgainstPC.fxml"));
        Parent root = loader.load();
        primaryStage.setScene(new Scene(root));
    }
}
