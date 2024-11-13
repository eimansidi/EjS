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
     * Configura el animal a editar y el Stage para este formulario.
     *
     * @param animal El animal a editar, o null para agregar uno nuevo.
     * @param stage El Stage asociado a este formulario.
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
     * Configura el Stage para este formulario.
     *
     * @param stage El Stage asociado a este formulario.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
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
            // Validar campos vacíos
            StringBuilder errores = new StringBuilder();
            if (nombreField.getText().isEmpty()) errores.append("Nombre\n");
            if (especieField.getText().isEmpty()) errores.append("Especie\n");
            if (razaField.getText().isEmpty()) errores.append("Raza\n");
            if (sexoField.getText().isEmpty()) errores.append("Sexo\n");
            if (edadField.getText().isEmpty()) {
                errores.append("Edad\n");
            } else {
                // Validar que Edad sea un número entero válido
                try {
                    Integer.parseInt(edadField.getText());
                } catch (NumberFormatException e) {
                    errores.append("Edad debe ser un número entero válido\n");
                }
            }
            if (pesoField.getText().isEmpty()) {
                errores.append("Peso\n");
            } else {
                // Validar que Peso sea un número decimal válido
                try {
                    Double.parseDouble(pesoField.getText());
                } catch (NumberFormatException e) {
                    errores.append("Peso debe ser un número válido\n");
                }
            }
            if (observacionesArea.getText().isEmpty()) errores.append("Observaciones\n");
            if (fechaPrimeraConsultaPicker.getValue() == null) errores.append("Fecha de Primera Consulta\n");

            // Si hay errores, mostrar mensaje y no continuar
            if (errores.length() > 0) {
                showAlert("Advertencia", "Por favor corrija los siguientes errores:\n" + errores, Alert.AlertType.WARNING);
                return;
            }

            // Continuar con la lógica de guardado si todos los campos son válidos
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

            if (stage != null) {
                stage.close();
            } else {
                showAlert("Error", "No se pudo cerrar la ventana: Stage no inicializado.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Error", "No se pudo guardar el animal", Alert.AlertType.ERROR);
        }
    }


    /**
     * Accion al presionar el boton de cancelar.
     */
    @FXML
    public void cancelar() {
        if (stage != null) {
            stage.close();
        } else {
            showAlert("Error", "No se pudo cerrar la ventana: Stage no inicializado.", Alert.AlertType.ERROR);
        }
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
