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
}
