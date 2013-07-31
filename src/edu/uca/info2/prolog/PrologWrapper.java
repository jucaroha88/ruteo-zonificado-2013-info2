/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.prolog;


import edu.uca.info2.components.Area;
import edu.uca.info2.components.Vehicle;
import java.util.ArrayList;
import java.util.List;


import jpl.Atom;
import jpl.Query;
import jpl.Term;
import jpl.Variable;


/**
 *
 * @author Toshiba
 */
public class PrologWrapper {
    private List<AsignacionVehiculoAreaHora> asignaciones;
    private List<Vehicle> vehiculos;
    private List<Area> areas;

    public PrologWrapper() {
        this.vehiculos = new ArrayList<Vehicle>();
        this.areas = new ArrayList<Area>();
        this.asignaciones = new ArrayList<AsignacionVehiculoAreaHora>();
    }
    
    public void consultar(){
        Variable asignvar = new Variable("asignaciones");
    }

    public List<AsignacionVehiculoAreaHora> getAsignaciones() {
        return asignaciones;
    }

    public void setVehiculos(List<Vehicle> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
    
    
    /*
     * main solo para pruebas
     */
    public static void main(String[] args) {
        
        Query q1 =
                new Query(
                "consult",
                new Term[]{new Atom("test.pl")});
        System.out.println( "consult " + (q1.query() ? "succeeded" : "failed"));
        
                
    }
}
