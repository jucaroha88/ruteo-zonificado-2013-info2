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

import java.io.FileNotFoundException;
import java.io.IOException;
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
//        Area a = (Area) map.getAreas().toArray()[0];

        for (Area a : map.getAreas()) {
            List<MapNode> path = rc.calculateRoute(a.getCenterNode(), a, ZARouteCalculator.SearchAlgorithms.DFS);
            System.out.println(path.toString());
            System.out.println("Nodos expandidos:" + ZASearchState.counter);
            ZASearchState.counter = 0;
        }

        PrologWrapper plw = new PrologWrapper(map);
        plw.consultar();
        for (AsignacionVehiculoAreaHora a : plw.getAsignaciones()) {
            System.out.println(a);
        }
    }

}
