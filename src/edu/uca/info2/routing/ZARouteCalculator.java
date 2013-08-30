package edu.uca.info2.routing;

import aima.core.agent.Action;
import aima.core.search.framework.GraphSearch;
import aima.core.search.framework.Problem;
import aima.core.search.framework.Search;
import aima.core.search.informed.AStarSearch;
import aima.core.search.uninformed.DepthFirstSearch;
import aimax.osm.data.MapWayFilter;
import aimax.osm.data.entities.MapNode;
import aimax.osm.routing.OsmMoveAction;
import aimax.osm.routing.RouteCalculator;
import edu.uca.info2.components.Area;

import java.util.ArrayList;
import java.util.List;

public class ZARouteCalculator extends RouteCalculator {

    // siempre usamos el camino en auto
    private static final int CAR_WAY = 1;

    public enum SearchAlgorithms {
        DFS,
        ASTAR,
    }

    public List<MapNode> calculateRoute(MapNode from, Area area, SearchAlgorithms sa) {
        List<MapNode> result = new ArrayList<MapNode>();
        MapWayFilter wayFilter = super.createMapWayFilter(area.getMap(),
                CAR_WAY);

        Problem problem = new Problem(new ZASearchState(from, area),
                new ZAActionsFunction(), new ZAResultsFunction(),
                new ZAGoalTest());

        Search search = null;

        if (sa == SearchAlgorithms.DFS) {
            search = new DepthFirstSearch(new GraphSearch());
        } else if (sa == SearchAlgorithms.ASTAR) {
            search = new AStarSearch(new GraphSearch(), new ZAUnvisitedSegmentsRemaining());
        }

        try {
            List<Action> actions = search.search(problem);
            for (Object action : actions) {
                if (action instanceof OsmMoveAction) {
                    OsmMoveAction a = (OsmMoveAction) action;
                    result.addAll(a.getNodes());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

}
