/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sueldoempleado;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Carlos
 */
public class BD {
    private static final String URL = "jdbc:mysql://uwuia09mwdn2gsr0:tFEeuvcvmvun3PwQOiTs@bepkhigtxfjmgblhn3yx-mysql.services.clever-cloud.com:3306/bepkhigtxfjmgblhn3yx";
    private static final String USUARIO = "uwuia09mwdn2gsr0";
    private static final String CONTRASENA = "tFEeuvcvmvun3PwQOiTs";

    private Connection conexion;

    public BD() {
        try {
            // Cargar el controlador JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión
            conexion = DriverManager.getConnection(URL, USUARIO, CONTRASENA);
            if (conexion != null) {
                System.out.println("Conexión exitosa a la base de datos MySQL");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace(); // Imprimir la traza completa del error
            System.err.println("Error al conectar a la base de datos: " + ex.getMessage());
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void cerrarConexion() {
        if (conexion != null) {
            try {
                conexion.close();
                System.out.println("Conexión cerrada correctamente");
            } catch (SQLException ex) {
                System.err.println("Error al cerrar la conexión: " + ex.getMessage());
            }
        }
    }
}