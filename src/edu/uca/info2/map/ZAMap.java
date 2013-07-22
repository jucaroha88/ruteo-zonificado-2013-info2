/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.map;

import aimax.osm.data.impl.DefaultMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.uca.info2.components.Area;
import edu.uca.info2.components.Zone;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;

/**
 *
 * @author Toshiba
 */
public class ZAMap extends DefaultMap {
    private Hashtable<String,Zone> zones;
    private Hashtable<Long,Area> areas;

    public ZAMap() {
        this.zones = new Hashtable<String,Zone>();
        this.areas = new Hashtable<Long,Area>();
    }

    public void addZone(Zone zone){
        zones.put(zone.getZoneId(), zone);
    }
    
    public void addArea(Area area){
        areas.put(area.getCenterNodeId(), area);
    }
        
    public Collection<Zone> getZones() {
        return zones.values();
    }

    public Collection<Area> getAreas() {
        return areas.values();
    }
    
    public void loadZonesFromJson(String jsonstr){
        Gson gson=new Gson();
        Type collectionType = new TypeToken<ArrayList<Zone>>(){}.getType();
        ArrayList<Zone> zones = gson.fromJson(jsonstr, collectionType);
        for(Zone zone : zones){
            zone.setMap(this);
            addZone(zone);
        }
    }
    
    public void loadAreasFromJson(String jsonstr){
        Gson gson=new Gson();
        Type collectionType = new TypeToken<ArrayList<Area>>(){}.getType();
        ArrayList<Area> areas = gson.fromJson(jsonstr, collectionType);
        for(Area area : areas){
            addArea(area);
        }
    }
}
