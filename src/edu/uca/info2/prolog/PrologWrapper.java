/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.prolog;


import edu.uca.info2.components.Area;
import edu.uca.info2.components.Vehicle;
import edu.uca.info2.map.ZAMap;
import edu.uca.info2.util.FileUtils;
import edu.uca.info2.viewer.ZAMapViewFrame;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;


import jpl.Atom;
import jpl.Compound;
import jpl.Query;
import jpl.Term;
import jpl.Util;
import jpl.Variable;


/**
 *
 * @author Toshiba
 */
public class PrologWrapper {
    private Collection<AsignacionVehiculoAreaHora> asignaciones;
    private Collection<Vehicle> vehiculos;
    private Collection<Area> areas;

    public PrologWrapper() {
        this(new ArrayList<Area>(),new ArrayList<Vehicle>());
    }
    
    public PrologWrapper(Collection<Area> areas, Collection<Vehicle> vehiculos){
        this.areas=areas;
        this.vehiculos=vehiculos;
        this.asignaciones=new ArrayList<AsignacionVehiculoAreaHora>(); 
    }
    
    /*
     * Realiza la consulta a la base de conocimientos, utilizando
     * sus listas de vehiculos y areas, y colocando los resultados 
     * en su lista de asignaciones. Se pueden obtener los resultados
     * con getAsignaciones()
     * 
     */
    public void consultar(){
        ArrayList<Term> termlist_areas = new ArrayList<Term>();
        ArrayList<Term> termlist_vehiculos = new ArrayList<Term>();
        Term pl_areas_list;
        Term pl_vehiculos_list;
        //armar lista de areas
        for(Area area : areas){
            if(area.getZone()==null)
                continue;
            if(area.getZone().getRestriction() == null)
                continue;
            termlist_areas.add(area.toPlCompoundTerm());
                
        }
        pl_areas_list = Util.termArrayToList(termlist_areas.toArray(new Term[termlist_areas.size()]));
        //armar lista de vehiculos
        for(Vehicle vehiculo : vehiculos){
            termlist_vehiculos.add(vehiculo.toPlCompoundTerm());
        }
        pl_vehiculos_list = Util.termArrayToList(termlist_vehiculos.toArray(new Term[termlist_vehiculos.size()]));
        //consulta
        Variable pl_variable_asignaciones = new Variable("Asignaciones");
        Query query = new Query("recorridosValidos", new Term[]{pl_variable_asignaciones, pl_vehiculos_list, pl_areas_list});
        //extraer resultados
        Hashtable<Variable,Term> solution = query.oneSolution();
        System.out.println(solution.get(pl_variable_asignaciones));
        /*
        Term[] termarray_asignaciones = Util.listToTermArray(solution.get(pl_variable_asignaciones));
        for(Term term : termarray_asignaciones){
            
        }
        */
    }

    public Collection<AsignacionVehiculoAreaHora> getAsignaciones() {
        return asignaciones;
    }

    public void setVehiculos(Collection<Vehicle> vehiculos) {
        this.vehiculos = vehiculos;
    }

    public void setAreas(Collection<Area> areas) {
        this.areas = areas;
    }
    
    
    /*
     * main solo para pruebas
     */
    public static void main(String[] args) throws FileNotFoundException,IOException{
        ArrayList<Vehicle> vehiculos = Vehicle.loadListFromJson(FileUtils.getContent("vehicles.json"));
        ZAMapViewFrame frame = new ZAMapViewFrame();
        ZAMap map = (ZAMap)frame.getView().getMap();
        //map.loadElementsFromJson();
        PrologWrapper prologuito = new PrologWrapper(map.getAreas(),vehiculos);
        prologuito.consultar();
    }
}
