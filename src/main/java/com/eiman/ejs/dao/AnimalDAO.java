package com.eiman.ejs.dao;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class AnimalDAO {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}