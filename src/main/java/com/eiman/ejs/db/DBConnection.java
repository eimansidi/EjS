package com.eiman.ejs.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Clase que gestiona la conexion con la base de datos SQLite y la inicializacion de la base de datos.
 */
public class DBConnection {

    // URL de conexion a la base de datos
    private static final String URL = "jdbc:sqlite:veterinario.db";

    /**
     * Obtiene una conexion a la base de datos.
     *
     * @return Conexion a la base de datos.
     * @throws SQLException Si ocurre un error al conectarse a la base de datos.
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    /**
     * Inicializa la base de datos, creando las tablas necesarias si no existen.
     */
    public static void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // SQL para crear la tabla "animals" si no existe
            String createTableSQL = """
                CREATE TABLE IF NOT EXISTS animals (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    nombre TEXT NOT NULL,
                    especie TEXT NOT NULL,
                    raza TEXT,
                    sexo TEXT NOT NULL,
                    edad INTEGER NOT NULL,
                    peso REAL NOT NULL,
                    observaciones TEXT,
                    fecha_primera_consulta TEXT NOT NULL,
                    foto TEXT
                );
            """;

            // Ejecuta la creacion de la tabla
            stmt.execute(createTableSQL);

            System.out.println("Base de datos inicializada correctamente.");
        } catch (SQLException e) {
            // Manejo de excepcion en caso de error al inicializar la base de datos
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
        }
    }
}