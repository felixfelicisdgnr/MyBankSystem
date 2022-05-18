package com.yeocak.proje3;

import com.yeocak.proje3.db.AuthRepository;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.*;

public class MainApp extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        if(!AuthRepository.setupSQL()){
            throw new NullPointerException("Can't setup the AuthRepository.");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(MainApp.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        stage.setTitle("Giriş yap");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}