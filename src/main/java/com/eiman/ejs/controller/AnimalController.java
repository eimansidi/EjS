package com.eiman.ejs.controller;

import com.eiman.ejs.dao.AnimalDAO;
import com.eiman.ejs.model.Animal;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controlador para la ventana principal de gestion de animales.
 */
public class AnimalController {

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Animal> animalTable;

    @FXML
    private TableColumn<Animal, String> colNombre;

    @FXML
    private TableColumn<Animal, String> colEspecie;

    @FXML
    private TableColumn<Animal, String> colRaza;

    @FXML
    private TableColumn<Animal, String> colSexo;

    @FXML
    private TableColumn<Animal, Integer> colEdad;

    @FXML
    private TableColumn<Animal, Double> colPeso;

    @FXML
    private TableColumn<Animal, String> colObservaciones;

    @FXML
    private TableColumn<Animal, String> colPrimeraConsulta;

    @FXML
    private Button addButton, editButton, deleteButton;

    private AnimalDAO animalDAO;

    private ObservableList<Animal> animalList;

    /**
     * Inicializa el controlador.
     */
    @FXML
    public void initialize() {
        try {
            animalDAO = new AnimalDAO();
            configureTable();
            loadTableData();

            // Configurar busqueda
            searchField.textProperty().addListener((observable, oldValue, newValue) -> filterAnimals(newValue));
        } catch (SQLException e) {
            showAlert("Error", "No se pudo conectar con la base de datos", Alert.AlertType.ERROR);
        }
    }

    /**
     * Configura las columnas de la tabla.
     */
    private void configureTable() {
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colEspecie.setCellValueFactory(new PropertyValueFactory<>("especie"));
        colRaza.setCellValueFactory(new PropertyValueFactory<>("raza"));
        colSexo.setCellValueFactory(new PropertyValueFactory<>("sexo"));
        colEdad.setCellValueFactory(new PropertyValueFactory<>("edad"));
        colPeso.setCellValueFactory(new PropertyValueFactory<>("peso"));
        colObservaciones.setCellValueFactory(new PropertyValueFactory<>("observaciones"));
        colPrimeraConsulta.setCellValueFactory(new PropertyValueFactory<>("ultimaRevision"));
    }

    /**
     * Carga los datos en la tabla desde la base de datos.
     */
    private void loadTableData() {
        try {
            List<Animal> animals = animalDAO.getAllAnimals();
            animalList = FXCollections.observableArrayList(animals);
            animalTable.setItems(animalList);
        } catch (SQLException e) {
            showAlert("Error", "No se pudieron cargar los datos", Alert.AlertType.ERROR);
        }
    }

    /**
     * Filtra los animales en la tabla segun el texto ingresado.
     *
     * @param filter Texto para filtrar los nombres.
     */
    private void filterAnimals(String filter) {
        if (filter == null || filter.isEmpty()) {
            animalTable.setItems(animalList);
        } else {
            List<Animal> filteredList = animalList.stream()
                    .filter(animal -> animal.getNombre().toLowerCase().contains(filter.toLowerCase()))
                    .collect(Collectors.toList());
            animalTable.setItems(FXCollections.observableArrayList(filteredList));
        }
    }

    /**
     * Abre el formulario para agregar un nuevo animal.
     */
    @FXML
    public void addAnimal() {
        openFormulario(null);
    }

    /**
     * Abre el formulario para editar el animal seleccionado.
     */
    @FXML
    public void editAnimal() {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();
        if (selectedAnimal != null) {
            openFormulario(selectedAnimal);
        } else {
            showAlert("Advertencia", "Por favor seleccione un animal para editar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Elimina el animal seleccionado de la base de datos.
     */
    @FXML
    public void deleteAnimal() {
        Animal selectedAnimal = animalTable.getSelectionModel().getSelectedItem();
        if (selectedAnimal != null) {
            Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION, "Esta seguro de eliminar el animal?", ButtonType.YES, ButtonType.NO);
            confirmation.showAndWait().ifPresent(response -> {
                if (response == ButtonType.YES) {
                    try {
                        animalDAO.deleteAnimal(selectedAnimal.getId());
                        animalList.remove(selectedAnimal);
                    } catch (SQLException e) {
                        showAlert("Error", "No se pudo eliminar el animal", Alert.AlertType.ERROR);
                    }
                }
            });
        } else {
            showAlert("Advertencia", "Por favor seleccione un animal para eliminar", Alert.AlertType.WARNING);
        }
    }

    /**
     * Abre el formulario para agregar o editar un animal.
     *
     * @param animal Animal a editar, o null para agregar uno nuevo.
     */
    private void openFormulario(Animal animal) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Formulario.fxml"));
            Parent root = loader.load();

            FormularioController controller = loader.getController();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            if (animal == null) {
                stage.setTitle("Agregar Animal");
            } else {
                stage.setTitle("Editar Animal");
                controller.setAnimal(animal, stage);
            }

            stage.showAndWait();
            loadTableData();
        } catch (IOException e) {
            showAlert("Error", "No se pudo abrir el formulario", Alert.AlertType.ERROR);
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
