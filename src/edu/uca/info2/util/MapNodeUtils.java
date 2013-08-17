package edu.uca.info2.util;

import java.util.ArrayList;
import java.util.List;

import edu.uca.info2.components.Area;
import aimax.osm.data.MapWayAttFilter;
import aimax.osm.data.MapWayFilter;
import aimax.osm.data.entities.MapNode;
import aimax.osm.data.entities.MapWay;
import aimax.osm.data.entities.WayRef;

public class MapNodeUtils {
	
	private static boolean ignoreOneWays;

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
						// segments.add(new SimpleEntry<MapNode, MapNode>(node,
						// to));
						// result.add(new OsmMoveAction(way, node, to));
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
							// segments.add(new SimpleEntry<MapNode, MapNode>(
							// node, to));
							// result.add(new OsmMoveAction(way, node, to));
							neighbors.add(to);
							break;
						}
					}
				}
			}
		}
		
		return neighbors;
	}

}
