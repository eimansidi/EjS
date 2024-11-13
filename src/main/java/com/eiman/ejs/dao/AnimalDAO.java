package com.eiman.ejs.dao;

import com.eiman.ejs.db.DBConnection;
import com.eiman.ejs.model.Animal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO para gestionar las operaciones CRUD en la tabla "animals".
 */
public class AnimalDAO {

    private Connection conn;

    /**
     * Constructor que inicializa la conexion a la base de datos.
     */
    public AnimalDAO() throws SQLException {
        this.conn = DBConnection.getConnection();
    }

    /**
     * Obtiene todos los registros de animales en la base de datos.
     *
     * @return Lista de animales.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public List<Animal> getAllAnimals() throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String query = "SELECT * FROM animals";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Animal animal = new Animal(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("especie"),
                        rs.getString("raza"),
                        rs.getString("sexo"),
                        rs.getInt("edad"),
                        rs.getDouble("peso"),
                        rs.getString("observaciones"),
                        rs.getString("fecha_primera_consulta"),
                        rs.getBytes("foto")
                );
                animals.add(animal);
            }
        }
        return animals;
    }

    /**
     * Cierra la conexion a la base de datos.
     *
     * @throws SQLException Si ocurre un error al cerrar la conexion.
     */
    public void closeConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }
}
