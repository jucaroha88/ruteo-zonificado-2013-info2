/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.application;

import aimax.osm.viewer.MapViewFrame;
import edu.uca.info2.components.Area;
import edu.uca.info2.components.Area;
import edu.uca.info2.map.ZAMap;
import edu.uca.info2.util.FileUtils;
import edu.uca.info2.viewer.ZAEntityRenderer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.List;

/**
 *  Aplicacion para visualizar las zonas y las areas descritas en los json
 * 
 * @author Toshiba
 */
public class ZonasYAreas {
    private static MapViewFrame frame;
    
    
    public static void main(String[] args) throws FileNotFoundException,IOException{
        inicializarFrame();
        ZAMap map = (ZAMap)frame.getView().getMap();
        
        //zonas desde json
        map.loadZonesFromJson(FileUtils.getContent("zones.json"));
        
        //areas desde json
        map.loadAreasFromJson(FileUtils.getContent("areas.json"));
    }
    
    private static void inicializarFrame(){
        frame = new MapViewFrame();
        frame.getView().setMap(new ZAMap());
        frame.getView().setRenderer(new ZAEntityRenderer());
        frame.readMap(new File("asu.osm"));
        frame.setTitle("Zonas y Areas");
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
