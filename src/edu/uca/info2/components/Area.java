package edu.uca.info2.components;

import aimax.osm.data.BoundingBox;
import aimax.osm.data.Position;
import aimax.osm.data.entities.MapNode;
import aimax.osm.data.entities.MapWay;
import edu.uca.info2.map.ZAMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import jpl.Atom;
import jpl.Compound;
import jpl.Term;

public class Area {

    private float costo; //costo de recorrer el area en km 
    private Zone zone;
    private ZAMap map;
    private long centerNodeId;
    private int radius;
    private List<MapNode> nodos;

    public Area(long centerNodeId) {
        setCenterNodeId(centerNodeId);
        setRadius(1);
    }

    public void setCosto(float costo) {
        this.costo = costo;
    }

    public float getCosto() {
        return costo;
    }

    
    
    public long getCenterNodeId() {
        return centerNodeId;
    }

    public void setCenterNodeId(long centerNodeId) {
        this.centerNodeId = centerNodeId;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public float getLat() {
        return new Position(map.getNode(centerNodeId)).getLat();
    }

    public float getLon() {
        return new Position(map.getNode(centerNodeId)).getLon();
    }

    public String toString() {

        return " Area: center: " + getCenterNodeId() + " radius: " + getRadius();
    }

    public List<MapNode> getNodos() {
        return nodos;
    }

    public void setMap(ZAMap map) {
        this.map = map;
    }

    public Zone getZone() {
        return zone;
    }

    /* busca los nodos dentro de su rango y los agrega a su lista de nodos */
    public void findNodes() {
        this.nodos = Collections.synchronizedList(new ArrayList<MapNode>());
        MapNode centernode = map.getNode(centerNodeId);
        for (MapWay mapway : map.getWays(new BoundingBox())) {
            for (MapNode nodo : mapway.getNodes()) {
                if (new Position(nodo).getDistKM(centernode) < 1) {
                    this.nodos.add(nodo);
                }
            }
        }
    }

    /*
     * establece la zona en la que se encuentra, basandose en las zonas de su mapa.
     */
    public void findZone() {
        for (Zone zona : map.getZones()) {
            float latmin = zona.getLat1();
            float latmax = zona.getLat2();
            float lonmin = zona.getLon1();
            float lonmax = zona.getLon2();
            //intercambiamos las coordenadas min y max para el caso de que esten al revez
            if (latmin > latmax) {
                float aux = latmin;
                latmin = latmax;
                latmax = aux;
            }
            if (lonmin > lonmax) {
                float aux = lonmin;
                lonmin = lonmax;
                lonmax = aux;
            }
            float lat = getLat();
            float lon = getLon();
            //establecemos la zona si el area pertenece a esta
            if (latmin <= lat && lat <= latmax && lonmin <= lon && lon <= lonmax) {
                this.zone = zona;
                break;
            }
        }
    }

    public Compound toPlCompoundTerm() {
        return new Compound("zona", new Term[]{new Atom(Long.toString(centerNodeId)),
                    new Atom(Integer.toString((int)Math.ceil(costo))),
                    new Atom(Integer.toString(zone.getRestriction().getStartTime())),
                    new Atom(Integer.toString(zone.getRestriction().getEndTime()))});
    }
}
