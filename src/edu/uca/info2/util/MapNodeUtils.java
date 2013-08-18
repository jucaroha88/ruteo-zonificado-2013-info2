package edu.uca.info2.util;

import java.util.ArrayList;
import java.util.List;

import edu.uca.info2.components.Area;
import edu.uca.info2.components.Segment;
import aimax.osm.data.MapWayAttFilter;
import aimax.osm.data.MapWayFilter;
import aimax.osm.data.entities.MapNode;
import aimax.osm.data.entities.MapWay;
import aimax.osm.data.entities.WayRef;

public class MapNodeUtils {

	// por default respetamos el sentido de las calles
	private static boolean ignoreOneWays = false;

	public static boolean isIgnoreOneWays() {
		return ignoreOneWays;
	}

	public static void setIgnoreOneWays(boolean ignoreOneWays) {
		MapNodeUtils.ignoreOneWays = ignoreOneWays;
	}

	public static List<MapNode> neighborsForNode(MapNode node, Area area) {
		List<MapNode> neighbors = new ArrayList<MapNode>();
		MapWayFilter filter = MapWayAttFilter.createCarWayFilter();

		for (WayRef wref : node.getWayRefs()) {
			if (filter == null || filter.isAccepted(wref.getWay())) {
				MapWay way = wref.getWay();
				int nodeIdx = wref.getNodeIdx();
				List<MapNode> wayNodes = way.getNodes();
				MapNode to;

				for (int idx = nodeIdx + 1; idx < wayNodes.size(); idx++) {
					to = wayNodes.get(idx);

					// Si el nodo to esta fuera del area continuamos
					if (!area.isNodeInArea(to)) {
						continue;
					}

					if (to.getWayRefs().size() > 1
							|| idx == wayNodes.size() - 1) {
						neighbors.add(to);
						break;
					}
				}
				if (!way.isOneway() || ignoreOneWays) {
					for (int idx = nodeIdx - 1; idx >= 0; idx--) {
						to = wayNodes.get(idx);

						// Si el nodo to esta fuera del area continuamos
						if (!area.isNodeInArea(to)) {
							continue;
						}

						if (to.getWayRefs().size() > 1 || idx == 0) {
							neighbors.add(to);
							break;
						}
					}
				}
			}
		}

		return neighbors;
	}

	public List<Segment> segmentsInArea(Area area) {
		List<Segment> segments = new ArrayList<Segment>();

		for (MapNode from : area.getNodos()) {
			for (MapNode to : MapNodeUtils.neighborsForNode(from, area)) {
				Segment s = new Segment(from, to);
				
				if (!segments.contains(s)) {
					segments.add(s);
				}
			}
		}

		return segments;
	}

}
