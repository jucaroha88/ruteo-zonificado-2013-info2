package edu.uca.info2.routing;

import java.util.ArrayList;
import java.util.List;

import edu.uca.info2.components.Area;
import aima.core.agent.Action;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.informed.AStarSearch;
import aimax.osm.data.MapWayFilter;
import aimax.osm.data.OsmMap;
import aimax.osm.data.entities.MapNode;
import aimax.osm.routing.RouteCalculator;

public class ZARouteCalculator extends RouteCalculator {

	// siempre usamos el camino en auto
	private static final int CAR_WAY = 1;

	public List<MapNode> calculateRoute(MapNode from, MapNode to, Area area) {
		List<MapNode> result = new ArrayList<MapNode>();
		MapWayFilter wayFilter = super.createMapWayFilter(area.getMap(), CAR_WAY);

		// Problem problem = createProblem(fromNode, toNode, map,
		// wayFilter, ignoreOneways, waySelection);
		// Search search = new AStarSearch(new GraphSearch(), hf);
		// List<Action> actions = search.search(problem);

		return result;
	}

}
