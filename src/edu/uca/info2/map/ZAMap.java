/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.map;

import aimax.osm.data.MapBuilder;
import aimax.osm.data.entities.MapNode;
import aimax.osm.data.impl.DefaultMap;
import aimax.osm.reader.Bz2OsmReader;
import aimax.osm.viewer.MapStyleFactory;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.uca.info2.components.Area;
import edu.uca.info2.components.Vehicle;
import edu.uca.info2.components.Zone;
import edu.uca.info2.components.ZoneRestriction;
import edu.uca.info2.util.FileUtils;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;

/**
 *
 * @author Toshiba
 */
public class ZAMap extends DefaultMap {
    
    private static final String MAP_FILE_NAME = "asu.osm";
    
    private MapNode selectedNode;
    private HashMap<MapNode, Area> nodosDemarcados;

    private Hashtable<String, Zone> zones;
    private Hashtable<Long, Area> areas;
    private Hashtable<String, Vehicle> vehicles;

    public void addNodoDemarcado(MapNode nodo, Area area){
        nodosDemarcados.put(nodo, area);
    }
    
    public HashMap<MapNode,Area> getNodosDemarcados(){
        return nodosDemarcados;
    }

    public ZAMap() {
        this.zones = new Hashtable<String, Zone>();
        this.areas = new Hashtable<Long, Area>();
        this.nodosDemarcados = new HashMap<MapNode,Area>();
		this.vehicles = new Hashtable<String,Vehicle>();
        readMap(new File(MAP_FILE_NAME));
        try{
            loadElementsFromJson();
        }catch(IOException e){
            System.err.println("problemas al cargar los elementos del json");
        }
    }

    public void addZone(Zone zone) {
        zones.put(zone.getZoneId(), zone);
    }

    public void addArea(Area area) {
        areas.put(area.getCenterNodeId(), area);
    }

    public Collection<Zone> getZones() {
        return zones.values();
    }

    public Collection<Area> getAreas() {
        return areas.values();
    }
    
    public Area getArea(Long centerNodeId){
        return areas.get(centerNodeId);
    }

    public Collection<Vehicle> getVehicles() {
        return vehicles.values();
    }
    
    public Vehicle getVehicle(String vehicleId){
        return vehicles.get(vehicleId);
    }
    
    public MapNode getSelectedNode() {
        return selectedNode;
    }

    public void setSelectedNode(MapNode selectedNode) {
        this.selectedNode = selectedNode;
    }
    
    protected void readMap(File file) {
            MapBuilder builder = getBuilder();
            builder.setEntityClassifier(new MapStyleFactory().createDefaultClassifier());
            (new Bz2OsmReader()).readMap(file, builder);
            builder.buildMap();
    }
    
    protected void loadElementsFromJson() throws FileNotFoundException, IOException {
        loadZonesFromJson(FileUtils.getContent("zones.json"));
        loadZoneRestrictionsFromJson(FileUtils.getContent("restrictions.json"));
        loadAreasFromJson(FileUtils.getContent("areas.json"));
        loadVehiclesFromJson(FileUtils.getContent("vehicles.json"));
    }

    protected void loadZonesFromJson(String jsonstr) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Zone>>() {
        }.getType();
        ArrayList<Zone> zones = gson.fromJson(jsonstr, collectionType);
        for (Zone zone : zones) {
            zone.setMap(this);
            addZone(zone);
        }
    }
    
    protected void loadZoneRestrictionsFromJson(String jsonstr){
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<ZoneRestriction>>() {
        }.getType();
        ArrayList<ZoneRestriction> zonerestrictions = gson.fromJson(jsonstr,collectionType);
        for(ZoneRestriction zonerestriction : zonerestrictions){
            zones.get(zonerestriction.getZoneId()).setRestriction(zonerestriction);
        }
    }

    protected void loadAreasFromJson(String jsonstr) {
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Area>>() {
        }.getType();
        ArrayList<Area> areas = gson.fromJson(jsonstr, collectionType);
        for (Area area : areas) {
            area.setMap(this);
            addArea(area);
            area.findNodes();
            area.findZone();
        }
    }
    
    protected void loadVehiclesFromJson(String jsonstr){
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Vehicle>>() {
        }.getType();
        ArrayList<Vehicle> vehiculos = gson.fromJson(jsonstr, collectionType);
        for(Vehicle vehicle : vehiculos){
            this.vehicles.put(vehicle.getVehicleId(), vehicle);
        }
    }
}
