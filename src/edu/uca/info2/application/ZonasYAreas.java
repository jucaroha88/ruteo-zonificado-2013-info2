/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.uca.info2.application;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import aimax.osm.data.entities.MapNode;
import edu.uca.info2.components.Area;
import edu.uca.info2.map.ZAMap;
import edu.uca.info2.routing.ZARouteCalculator;
import edu.uca.info2.util.FileUtils;
import edu.uca.info2.viewer.ZAMapViewFrame;

/**
 * Aplicacion para visualizar las zonas y las areas descritas en los json
 * 
 * @author Toshiba
 */
public class ZonasYAreas {
	private static ZAMapViewFrame frame;

	public static void main(String[] args) throws FileNotFoundException,
			IOException {
		inicializarFrame();
		ZAMap map = (ZAMap) frame.getView().getMap();

		// zonas desde json
		map.loadZonesFromJson(FileUtils.getContent("zones.json"));

		// areas desde json
		map.loadAreasFromJson(FileUtils.getContent("areas.json"));

		ZARouteCalculator rc = new ZARouteCalculator();
		Area a = (Area) map.getAreas().toArray()[0];
		List<MapNode> path = rc.calculateRoute(a.getCenterNode(), null, a);
		System.out.println(path.toString());
	}

	private static void inicializarFrame() {
		frame = new ZAMapViewFrame();
	}
}
