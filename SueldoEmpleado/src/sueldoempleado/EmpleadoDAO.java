/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sueldoempleado;

/**
 *
 * @author Carlos
 */
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {

    private Connection conexion;

    public EmpleadoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }

    public void agregarEmpleado(Empleado empleado) {
        String query = "INSERT INTO empleado (ID_empleado, Nombre, Fecha_nacimiento, Fecha_contratacion, Telefono, Id_cargo, Correo) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try ( PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, empleado.getIdEmpleado()); // Insertar el ID del empleado
            statement.setString(2, empleado.getNombre());
            statement.setDate(3, Date.valueOf(empleado.getFechaNacimiento()));
            statement.setDate(4, Date.valueOf(empleado.getFechaContratacion()));
            statement.setString(5, empleado.getTelefono());
            statement.setInt(6, empleado.getIdCargo());
            statement.setString(7, empleado.getCorreo());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        List<Empleado> empleados = new ArrayList<>();
        String query = "SELECT * FROM empleado";
        try ( Statement statement = conexion.createStatement();  ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()) {
                int idEmpleado = resultSet.getInt("id_empleado");
                String nombre = resultSet.getString("nombre");
                LocalDate fechaNacimiento = resultSet.getDate("fecha_nacimiento").toLocalDate();
                LocalDate fechaContratacion = resultSet.getDate("fecha_contratacion").toLocalDate();
                String telefono = resultSet.getString("telefono");
                int idCargo = resultSet.getInt("id_cargo");
                String correo = resultSet.getString("correo");

                Empleado empleado = new Empleado(idEmpleado, nombre, fechaNacimiento, fechaContratacion, telefono, idCargo, correo);
                empleados.add(empleado);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return empleados;
    }

    public void actualizarEmpleado(Empleado empleado) {

        String query = "UPDATE empleado SET Nombre = ?, Fecha_nacimiento = ?, Fecha_contratacion = ?, Telefono = ?, Id_cargo = ?, Correo = ? WHERE ID_empleado = ?";
        try ( PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setString(1, empleado.getNombre());
            statement.setDate(2, Date.valueOf(empleado.getFechaNacimiento()));
            statement.setDate(3, Date.valueOf(empleado.getFechaContratacion()));
            statement.setString(4, empleado.getTelefono());
            statement.setInt(5, empleado.getIdCargo());
            statement.setString(6, empleado.getCorreo());
            statement.setInt(7, empleado.getIdEmpleado()); // El ID se usa en la cláusula WHERE
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void eliminarEmpleado(Empleado empleado) {
        // Obtener el ID del empleado
        int idEmpleado = empleado.getIdEmpleado();

        String query = "DELETE FROM empleado WHERE ID_empleado = ?";
        try ( PreparedStatement statement = conexion.prepareStatement(query)) {
            statement.setInt(1, idEmpleado);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
