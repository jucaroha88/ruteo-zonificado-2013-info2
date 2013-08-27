package edu.uca.info2.util;

import java.util.ArrayList;
import java.util.List;

import aimax.osm.data.MapWayAttFilter;
import aimax.osm.data.MapWayFilter;
import aimax.osm.data.entities.MapNode;
import aimax.osm.data.entities.MapWay;
import aimax.osm.data.entities.WayRef;
import edu.uca.info2.components.Area;

public class MapUtils {

	// por default respetamos el sentido de las calles
	private static boolean ignoreOneways = true;

	public static void setIgnoreOneways(boolean ignoreOneways) {
		MapUtils.ignoreOneways = ignoreOneways;
	}

    public static boolean getIgnoreOneways() {
        return ignoreOneways;
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
				if (!way.isOneway() || ignoreOneways) {
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

}
