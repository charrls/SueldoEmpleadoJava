/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sueldoempleado;

import java.time.LocalDate;

/**
 *
 * @author Carlos
 */
public class Empleado {
    private int idEmpleado;
    private String nombre;
    private LocalDate fechaNacimiento;
    private LocalDate fechaContratacion;
    private String telefono;
    private int idCargo;
    private String correo;

    // Constructor
    public Empleado(int idEmpleado, String nombre, LocalDate fechaNacimiento, LocalDate fechaContratacion, String telefono, int idCargo, String correo) {
        this.idEmpleado = idEmpleado;
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaContratacion = fechaContratacion;
        this.telefono = telefono;
        this.idCargo = idCargo;
        this.correo = correo;
    }

    // Getters y setters
    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }

    public void setFechaContratacion(LocalDate fechaContratacion) {
        this.fechaContratacion = fechaContratacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public int getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(int idCargo) {
        this.idCargo = idCargo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    // Método toString para representación de texto del objeto
    @Override
    public String toString() {
        return "Empleado{" +
                "idEmpleado=" + idEmpleado +
                ", nombre='" + nombre + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                ", fechaContratacion=" + fechaContratacion +
                ", telefono='" + telefono + '\'' +
                ", idCargo=" + idCargo +
                ", correo='" + correo + '\'' +
                '}';
    }
}

