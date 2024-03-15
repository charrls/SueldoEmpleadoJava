/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sueldoempleado;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 *
 * @author Carlos
 */
public class CalcularSalario {

    public static double calcularSalarioTotal(double horasTrabajadas, double pagoPorHoraBase, double horasExtras, double pagoPorHoraExtra, LocalDate fechaInicio) {
        double bonoAntiguedad = calcularBonoAntiguedad(fechaInicio);
        return (horasTrabajadas * pagoPorHoraBase) + (horasExtras * pagoPorHoraExtra) + ((horasTrabajadas * pagoPorHoraBase) * bonoAntiguedad);
    }

    private static double calcularBonoAntiguedad(LocalDate fechaInicio) {
        LocalDate fechaActual = LocalDate.now();
        long añosTrabajados = ChronoUnit.YEARS.between(fechaInicio, fechaActual);
        return añosTrabajados * 0.05;
    }
}