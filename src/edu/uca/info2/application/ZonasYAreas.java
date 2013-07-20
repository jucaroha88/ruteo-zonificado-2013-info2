/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.application;

import aimax.osm.data.entities.MapNode;
import aimax.osm.viewer.MapViewFrame;
import edu.uca.info2.components.Zone;
import edu.uca.info2.components.ZoneRestriction;
import edu.uca.info2.map.ZAMap;
import edu.uca.info2.viewer.ZAEntityRenderer;
import java.io.File;

/**
 *
 * @author Toshiba
 */
public class ZonasYAreas {
    private static MapViewFrame frame;
    
    
    public static void main(String[] args) {
        inicializarFrame();
        ZAMap map = (ZAMap)frame.getView().getMap();
        long[] posis = {250042889,250054645,314446095, 319483759,
                        314446095, 319483759, 1439400386, 593976739,
                        1439400386, 593976739, 59712800, 613343696,
        
                        250042803,
                        312457840,
                        314446085,
                        330747283,
                        692261833,
                        1440628204
                        };
        
        map.addZone(new Zone(250042889,250054645,314446095, 319483759,"leZone1",null,map));
        map.addZone(new Zone(314446095, 319483759, 1439400386, 593976739,"leZone2",null,map));
        map.addZone(new Zone(1439400386, 593976739, 59712800, 613343696,"leZone3",null,map));
        
    }
    
    private static void inicializarFrame(){
        frame = new MapViewFrame();
        frame.getView().setMap(new ZAMap());
        frame.getView().setRenderer(new ZAEntityRenderer());
        frame.readMap(new File("asu.osm"));
        ZAMap map = (ZAMap) frame.getView().getMap();
        //map.addZone(new zone(0,0,0,0,"le test zone", null));
        frame.setTitle("Zonas y Areas");
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
