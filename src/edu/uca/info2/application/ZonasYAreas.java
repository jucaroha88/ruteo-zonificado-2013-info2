/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.application;

import edu.uca.info2.map.ZAMap;
import edu.uca.info2.util.FileUtils;
import edu.uca.info2.viewer.ZAMapViewFrame;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *  Aplicacion para visualizar las zonas y las areas descritas en los json
 * 
 * @author Toshiba
 */
public class ZonasYAreas {
    private static ZAMapViewFrame frame;
    
    
    public static void main(String[] args) throws FileNotFoundException,IOException{
        frame = new ZAMapViewFrame();
        ZAMap map = (ZAMap)frame.getView().getMap();
        map.loadElementsFromJson();
    }
}
