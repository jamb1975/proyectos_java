/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.text.ParseException;
import java.util.List;
import java.util.stream.Collectors;
import modelo.CargosEntidad;
import modelo.Empleados;
import fxnomina.FXNomina;

/**
 *
 * @author adminlinux
 */
public class BusquedasListasMemoria {

    public static List<Empleados> searchEmpleados(String searchString) throws ParseException {

        return FXNomina.li_empleados.stream().filter(line -> line.getGenPersonas().getNombres().toUpperCase().contains(searchString.toUpperCase()) || line.getGenPersonas().getDocumento().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());

    }

    public static List<CargosEntidad> searchCargosEntidad(String searchString) throws ParseException {

        return FXNomina.li_CargosEntidads.stream().filter(line -> line.getNombre().toUpperCase().contains(searchString.toUpperCase())) //filters the line, equals to "mkyong"
                .collect(Collectors.toList());

    }
}
