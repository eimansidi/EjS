package com.eiman.ejs.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AnimalController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}