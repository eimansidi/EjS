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
     * Inserta un nuevo animal en la base de datos.
     *
     * @param animal Objeto Animal a insertar.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void insertAnimal(Animal animal) throws SQLException {
        String query = "INSERT INTO animals (nombre, especie, raza, sexo, edad, peso, observaciones, fecha_primera_consulta, foto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, animal.getNombre());
            pstmt.setString(2, animal.getEspecie());
            pstmt.setString(3, animal.getRaza());
            pstmt.setString(4, animal.getSexo());
            pstmt.setInt(5, animal.getEdad());
            pstmt.setDouble(6, animal.getPeso());
            pstmt.setString(7, animal.getObservaciones());
            pstmt.setString(8, animal.getFechaPrimeraConsulta());
            pstmt.setBytes(9, animal.getFoto());
            pstmt.executeUpdate();
        }
    }

    /**
     * Actualiza un registro de animal existente en la base de datos.
     *
     * @param animal Objeto Animal con los datos actualizados.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void updateAnimal(Animal animal) throws SQLException {
        String query = "UPDATE animals SET nombre = ?, especie = ?, raza = ?, sexo = ?, edad = ?, peso = ?, observaciones = ?, fecha_primera_consulta = ?, foto = ? WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, animal.getNombre());
            pstmt.setString(2, animal.getEspecie());
            pstmt.setString(3, animal.getRaza());
            pstmt.setString(4, animal.getSexo());
            pstmt.setInt(5, animal.getEdad());
            pstmt.setDouble(6, animal.getPeso());
            pstmt.setString(7, animal.getObservaciones());
            pstmt.setString(8, animal.getFechaPrimeraConsulta());
            pstmt.setBytes(9, animal.getFoto());
            pstmt.setInt(10, animal.getId());
            pstmt.executeUpdate();
        }
    }

    /**
     * Elimina un registro de animal de la base de datos.
     *
     * @param id Identificador unico del animal a eliminar.
     * @throws SQLException Si ocurre un error en la base de datos.
     */
    public void deleteAnimal(int id) throws SQLException {
        String query = "DELETE FROM animals WHERE id = ?";

        try (PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, id);
            pstmt.executeUpdate();
        }
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
