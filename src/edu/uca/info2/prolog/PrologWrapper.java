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
    public static final String knowledgeBaseFile = "autos_zonas_horas.pl";
    
    private Collection<AsignacionVehiculoAreaHora> asignaciones;
    private Collection<Vehicle> vehiculos;
    private Collection<Area> areas;
    
    private ZAMap map;
    
    private Query lastQuery;
    
    public PrologWrapper(ZAMap map) throws PrologWrapperException{
        this.map=map;
        this.areas=map.getAreas();
        this.vehiculos=map.getVehicles();
        this.asignaciones=new ArrayList<AsignacionVehiculoAreaHora>(); 
        //consultamos la base de conocimientos
        Query kbconsultquery = new Query( "consult", new Term[]{new Atom(knowledgeBaseFile)} );
        if(!kbconsultquery.query()){
            throw new PrologWrapperException("No se pudo consultar la base de conocimientos "+knowledgeBaseFile);
        }
    }

    public Query getLastQuery() {
        return lastQuery;
    }
    
    
    
    /*
     * Realiza la consulta a la base de conocimientos, utilizando
     * sus listas de vehiculos y areas, y colocando los resultados 
     * en su lista de asignaciones. Se pueden obtener los resultados
     * con getAsignaciones().
     * NOTA: las areas sin zona, restricciones, o costo, seran ignoradas
     * 
     */
    public void consultar() throws PrologWrapperException{
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
            Term t=vehiculo.toPlCompoundTerm();
            termlist_vehiculos.add(t);
        }

        pl_vehiculos_list = Util.termArrayToList(termlist_vehiculos.toArray(new Term[termlist_vehiculos.size()]));

        //consulta
        String variableName = "Asignaciones";
        Variable pl_variable_asignaciones = new Variable(variableName);
        Query query = new Query("recorridosValidos", new Term[]{pl_variable_asignaciones, pl_vehiculos_list, pl_areas_list});
        
        this.lastQuery = query;
        
        //extraer resultados
        this.asignaciones=new ArrayList<AsignacionVehiculoAreaHora>();
        Hashtable<String,Term> solution = query.oneSolution();
        if(solution != null){
            Term[] termlist_asignaciones = Util.listToTermArray( solution.get(variableName));
            for(Term t : termlist_asignaciones){
                asignaciones.add(new AsignacionVehiculoAreaHora(t, this.map));
            }
        }      
        
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
    public static void main(String[] args) throws FileNotFoundException,IOException, PrologWrapperException{
        ZAMapViewFrame frame = new ZAMapViewFrame();
        ZAMap map = (ZAMap)frame.getView().getMap();
        PrologWrapper prologuito = new PrologWrapper(map);
        prologuito.consultar();
        
        System.out.println(prologuito.getLastQuery());
        
        for(AsignacionVehiculoAreaHora a : prologuito.getAsignaciones()){
            System.out.println(a);
        }
    }
    
}
