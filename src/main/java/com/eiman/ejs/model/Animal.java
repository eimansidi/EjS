package com.eiman.ejs.model;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class Animal {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}