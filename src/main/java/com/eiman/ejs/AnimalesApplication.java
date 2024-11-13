package com.eiman.ejs;

import com.eiman.ejs.db.DBConnection;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AnimalesApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Inicializar base de datos
        DBConnection.initializeDatabase();

        FXMLLoader fxmlLoader = new FXMLLoader(AnimalesApplication.class.getResource("/fxml/EjS.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("GESTION DE VETERINARIO");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}