/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.prolog;

import edu.uca.info2.components.Area;
import edu.uca.info2.components.Vehicle;
import edu.uca.info2.map.ZAMap;
import jpl.Atom;
import jpl.Term;

/**
 *
 * @author Toshiba
 */
public class AsignacionVehiculoAreaHora {
    public static final String pl_compound_name = "recorrido";
    
    Vehicle vehiculo;
    Area area;
    int hora;
    
    /*
     * los atributos de vehiculo, area y hora seran referencia a los correspondientes en
     * el map. Si el elemento no existe se levanta una excepcion
     */
    public AsignacionVehiculoAreaHora(Term term, ZAMap map) throws PrologWrapperException{
        if(!term.name().equals(pl_compound_name)){
            throw new PrologWrapperException("esto no es un compound "+pl_compound_name);
        }
        Term term_auto=term.arg(1);
        Term term_zona=term.arg(2);
        Term term_hora=term.arg(3);
        
        this.vehiculo=map.getVehicle(term_auto.arg(1).name());
        this.area=map.getArea(term_zona.arg(1).longValue());
        this.hora=term_hora.intValue();
    }
    
    public AsignacionVehiculoAreaHora(Vehicle vehiculo, Area area, int hora) {
        this.vehiculo = vehiculo;
        this.area = area;
        this.hora = hora;
    }
    
    public String toString(){
        return "asignacion("+vehiculo+","+area+","+hora+")";
    }
}
