package com.eiman.ejs.db;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DBConnection {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}