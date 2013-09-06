/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.application;

import aimax.osm.data.entities.MapNode;
import edu.uca.info2.components.Area;
import edu.uca.info2.map.ZAMap;
import edu.uca.info2.prolog.AsignacionVehiculoAreaHora;
import edu.uca.info2.prolog.PrologWrapper;
import edu.uca.info2.prolog.PrologWrapperException;
import edu.uca.info2.routing.ZARouteCalculator;
import edu.uca.info2.routing.ZASearchState;
import edu.uca.info2.viewer.ZAMapViewFrame;

import java.io.*;
import java.util.List;

/**
 * Aplicacion para visualizar las zonas y las areas descritas en los json
 *
 * @author Toshiba
 */
public class ZonasYAreas {
    private static ZAMapViewFrame frame;

    public static void main(String[] args) throws FileNotFoundException,
            IOException,
            PrologWrapperException {

        frame = new ZAMapViewFrame();
        ZAMap map = (ZAMap) frame.getView().getMap();
        ZARouteCalculator rc = new ZARouteCalculator();
        StringBuilder sb = new StringBuilder();

        for (Area a : map.getAreas()) {
            List<MapNode> path = rc.calculateRoute(a.getCenterNode(), a, ZARouteCalculator.SearchAlgorithms.DFS);
            System.out.println(path.toString());
            sb.append(path.toString());
            sb.append("\n");
            System.out.println("Nodos expandidos:" + ZASearchState.counter);
            ZASearchState.counter = 0;
        }

        saveResultToFile("resultado-java.txt", sb.toString());
        sb = new StringBuilder();

        PrologWrapper plw = new PrologWrapper(map);
        plw.consultar();
        for (AsignacionVehiculoAreaHora a : plw.getAsignaciones()) {
            System.out.println(a);
            sb.append(a);
            sb.append("\n");
        }

        saveResultToFile("resultado-prolog.txt", sb.toString());
    }

    private static void saveResultToFile(String filename,String result) throws IOException{
        File file = new File(filename);
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(result);
        writer.close();
    }

}
