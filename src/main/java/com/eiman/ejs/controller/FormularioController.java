package com.eiman.ejs.controller;

import com.eiman.ejs.dao.AnimalDAO;
import com.eiman.ejs.model.Animal;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;

/**
 * Controlador para el formulario de agregar o editar animales.
 */
public class FormularioController {

    @FXML
    private TextField nombreField;

    @FXML
    private TextField especieField;

    @FXML
    private TextField razaField;

    @FXML
    private TextField sexoField;

    @FXML
    private TextField edadField;

    @FXML
    private TextField pesoField;

    @FXML
    private TextArea observacionesArea;

    @FXML
    private DatePicker fechaPrimeraConsultaPicker;

    @FXML
    private DatePicker ultimaRevisionPicker;

    @FXML
    private Label fotoLabel;

    @FXML
    private Button guardarButton, cancelarButton, cargarFotoButton;

    private AnimalDAO animalDAO;
    private Animal animal;
    private Stage stage;
    private byte[] fotoBytes;

    /**
     * Inicializa el controlador.
     */
    @FXML
    public void initialize() {
        try {
            animalDAO = new AnimalDAO();
        } catch (SQLException e) {
            showAlert("Error", "No se pudo conectar con la base de datos", Alert.AlertType.ERROR);
        }
    }

    /**
     * Configura la ventana con el animal a editar, o limpia los campos para un nuevo animal.
     *
     * @param animal El animal a editar, o null si se agrega uno nuevo.
     */
    public void setAnimal(Animal animal, Stage stage) {
        this.animal = animal;
        this.stage = stage;

        if (animal != null) {
            nombreField.setText(animal.getNombre());
            especieField.setText(animal.getEspecie());
            razaField.setText(animal.getRaza());
            sexoField.setText(animal.getSexo());
            edadField.setText(String.valueOf(animal.getEdad()));
            pesoField.setText(String.valueOf(animal.getPeso()));
            observacionesArea.setText(animal.getObservaciones());
            fechaPrimeraConsultaPicker.setValue(java.time.LocalDate.parse(animal.getFechaPrimeraConsulta()));
            fotoBytes = animal.getFoto();
            fotoLabel.setText(fotoBytes != null ? "Foto cargada" : "Sin foto");
        }
    }

    /**
     * Accion al presionar el boton de cargar foto.
     */
    @FXML
    public void cargarFoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Imagenes", "*.png", "*.jpg", "*.jpeg"));
        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            try {
                fotoBytes = Files.readAllBytes(file.toPath());
                fotoLabel.setText("Foto cargada");
            } catch (IOException e) {
                showAlert("Error", "No se pudo cargar la foto", Alert.AlertType.ERROR);
            }
        }
    }

    /**
     * Accion al presionar el boton de guardar.
     */
    @FXML
    public void guardar() {
        try {
            if (animal == null) {
                animal = new Animal();
            }

            animal.setNombre(nombreField.getText());
            animal.setEspecie(especieField.getText());
            animal.setRaza(razaField.getText());
            animal.setSexo(sexoField.getText());
            animal.setEdad(Integer.parseInt(edadField.getText()));
            animal.setPeso(Double.parseDouble(pesoField.getText()));
            animal.setObservaciones(observacionesArea.getText());
            animal.setFechaPrimeraConsulta(fechaPrimeraConsultaPicker.getValue().toString());
            animal.setFoto(fotoBytes);

            if (animal.getId() == 0) {
                animalDAO.insertAnimal(animal);
            } else {
                animalDAO.updateAnimal(animal);
            }

            stage.close();
        } catch (SQLException e) {
            showAlert("Error", "No se pudo guardar el animal", Alert.AlertType.ERROR);
        } catch (NumberFormatException e) {
            showAlert("Error", "Por favor ingrese valores numericos validos para Edad y Peso", Alert.AlertType.WARNING);
        }
    }

    /**
     * Accion al presionar el boton de cancelar.
     */
    @FXML
    public void cancelar() {
        stage.close();
    }

    /**
     * Muestra una alerta con un mensaje especifico.
     *
     * @param title Titulo de la alerta.
     * @param content Contenido del mensaje.
     * @param type Tipo de alerta.
     */
    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
